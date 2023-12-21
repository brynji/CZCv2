package cz.cvut.fit.tjv.czcv2.controller;

import cz.cvut.fit.tjv.czcv2.domain.Product;
import cz.cvut.fit.tjv.czcv2.service.BuyerService;
import cz.cvut.fit.tjv.czcv2.service.ProductService;
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
@RequestMapping(value ="/product")
public class ProductController {
    private final ProductService productService;
    private final BuyerService buyerService;

    public ProductController(ProductService productService, BuyerService buyerService) {
        this.productService = productService;
        this.buyerService = buyerService;
    }

    @PostMapping
    @Operation(description = "add new product to stock")
    public Product create(@RequestBody Product data) {
        data.setId(0L);
        data.setRating(0);
        return productService.create(data);
    }

    @GetMapping
    @Operation(description = "return all products")
    @Parameter(name = "cost", description = "return only products with cost less than or equal param")
    @Parameter(name = "available", description = "return only products with available number in stock at least param")
    @Parameter(name = "rating", description = "return only products with rating at least param")
    @ApiResponses(value = { @ApiResponse( content = {
            @Content( mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Product.class)))})})
    public Iterable<Product> get(@RequestParam Optional<Integer> cost, @RequestParam Optional<Integer> available, @RequestParam Optional<Integer> rating){
        int costNum=Integer.MAX_VALUE, availableNum=0, ratingNum=0;

        if(cost.isPresent()){ costNum=cost.get(); }
        if(available.isPresent()){ availableNum=available.get(); }
        if(rating.isPresent()) { ratingNum=rating.get(); }

        return productService.getAllWithFilters(costNum,availableNum,ratingNum);
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "return operation with given id")
    @Parameter(description = "id of product that should be returned")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "product with given id does not exist", content=@Content)
    })
    public Product get(@PathVariable Long id){
        Optional<Product> res = productService.readById(id);
        if(res.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return res.get();
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "overwrite product with given id")
    @Parameter(description = "id of product that should be edited")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "product with given id does not exist", content=@Content),
            @ApiResponse(responseCode = "409", description = "id of given product does not match id of product that should be edited", content=@Content)
    })
    public void update(@PathVariable Long id, @RequestBody Product data){
        try{
            productService.update(id,data);
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value = "/{id}")
    @Operation(description = "remove product with given id from stock")
    @Parameter(description = "id of product that should be removed")
    public void delete(@PathVariable Long id){
        buyerService.removeFromBought(id);
        productService.deleteById(id);
    }
}
