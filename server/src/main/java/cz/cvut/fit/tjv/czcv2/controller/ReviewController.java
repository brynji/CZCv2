package cz.cvut.fit.tjv.czcv2.controller;

import cz.cvut.fit.tjv.czcv2.domain.Buyer;
import cz.cvut.fit.tjv.czcv2.domain.Product;
import cz.cvut.fit.tjv.czcv2.domain.Review;
import cz.cvut.fit.tjv.czcv2.dto.ReviewDTO;
import cz.cvut.fit.tjv.czcv2.service.BuyerService;
import cz.cvut.fit.tjv.czcv2.service.ProductService;
import cz.cvut.fit.tjv.czcv2.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(value = "/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final BuyerService buyerService;
    private final ProductService productService;

    public ReviewController(ReviewService reviewService, BuyerService buyerService, ProductService productService) {
        this.reviewService = reviewService;
        this.buyerService = buyerService;
        this.productService = productService;
    }

    @PostMapping
    @Operation(description = "Create new review")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "author or product with id given in request body does not exist", content=@Content),
            @ApiResponse(responseCode = "409", description = "author already posted review of that product", content=@Content)
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
    @ApiResponses(value = { @ApiResponse( content = {
            @Content( mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Review.class)))})})
    public Iterable<Review> get(){
        return reviewService.readAll();
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "return review with given id")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "review with given id does not exist", content=@Content)
    })
    public Review get(@PathVariable Long id){
        Optional<Review> res = reviewService.readById(id);
        if(res.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return res.get();
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "overwrite review with given id")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "review with given id does not exist", content=@Content),
            @ApiResponse(responseCode = "409", description = "id of given review does not match id of review that should be edited", content=@Content)
    })
    public void edit(@PathVariable Long id, @RequestBody ReviewDTO dto){
        Optional<Buyer> buyer = buyerService.readById(dto.getAuthorId());
        Optional<Product> product = productService.readById(dto.getProductId());

        Review data = new Review(dto.getId(), dto.getRating(), dto.getComment(), buyer.get(), product.get());

        try{
            reviewService.update(id,data);
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value = "/{id}")
    @Operation(description = "delete review with given id")
    @Parameter(description = "id of review that should be removed")
    public void delete(@PathVariable Long id){
        reviewService.deleteById(id);
    }

}
