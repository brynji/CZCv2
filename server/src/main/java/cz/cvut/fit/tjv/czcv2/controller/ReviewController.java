package cz.cvut.fit.tjv.czcv2.controller;

import com.fasterxml.jackson.databind.JsonNode;
import cz.cvut.fit.tjv.czcv2.domain.Buyer;
import cz.cvut.fit.tjv.czcv2.domain.Product;
import cz.cvut.fit.tjv.czcv2.domain.Review;
import cz.cvut.fit.tjv.czcv2.dto.ReviewDTO;
import cz.cvut.fit.tjv.czcv2.service.BuyerService;
import cz.cvut.fit.tjv.czcv2.service.ProductService;
import cz.cvut.fit.tjv.czcv2.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(value = "/review")
public class ReviewController {
    private ReviewService reviewService;
    private BuyerService buyerService;
    private ProductService productService;

    public ReviewController(ReviewService reviewService, BuyerService buyerService, ProductService productService) {
        this.reviewService = reviewService;
        this.buyerService = buyerService;
        this.productService = productService;
    }

    @PostMapping
    @Operation(description = "Create new review")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "author or product with id given in request body does not exist"),
            @ApiResponse(responseCode = "409", description = "author already posted review of that product")
    })
    public Review create(@RequestBody ReviewDTO dto){
        dto.setId(0L);
        Optional<Buyer> buyer = buyerService.readById(dto.getAuthorId());
        Optional<Product> product = productService.readById(dto.getProductId());
        if(buyer.isEmpty() || product.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Review data = new Review(dto.getId(), dto.getRating(), dto.getComment(), buyer.get(), product.get());

        try{
            return reviewService.create(data);
        } catch (UnsupportedOperationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    @Operation(description = "return all reviews")
    public Iterable<Review> get(){
        return reviewService.readAll();
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "return review with given id")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "review with given id does not exist")
    })
    public Review get(@PathVariable Long id){
        Optional<Review> res = reviewService.readById(id);
        if(res.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return res.get();
    }

    @PostMapping(value = "/{id}")
    @Operation(description = "overwrite review with given id")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "review with given id does not exist"),
            @ApiResponse(responseCode = "409", description = "id of given review does not match id of review that should be edited")
    })
    public void edit(@PathVariable Long id, @RequestBody Review data){
        try{
            reviewService.update(id,data);
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping(value = "/{id}")
    @Operation(description = "edit review with given id")
    @Parameter(description = "id of review that should be edited")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "review with given id does not exist")
    })
    public void patch(@PathVariable Long id, @RequestBody JsonNode data){
        Optional<Review> saved = reviewService.readById(id);
        if(saved.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Review toEdit = saved.get();

        if(data.has("rating")) toEdit.setRating(data.get("rating").asInt());
        if(data.has("comment")) toEdit.setComment(data.get("comment").asText());

        reviewService.update(id,toEdit);
        productService.updateProductRating(saved.get().getProduct().getId());
    }

    @DeleteMapping(value = "/{id}")
    @Operation(description = "delete review with given id")
    @Parameter(description = "id of review that should be removed")
    public void delete(@PathVariable Long id){
        reviewService.deleteById(id);
    }

}
