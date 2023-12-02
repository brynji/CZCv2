package cz.cvut.fit.tjv.czcv2.controller;

import com.fasterxml.jackson.databind.JsonNode;
import cz.cvut.fit.tjv.czcv2.domain.Product;
import cz.cvut.fit.tjv.czcv2.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping(value ="/product")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
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
    @Parameter(name = "cost", description = "return all products with cost less than or equal param")
    @Parameter(name = "available", description = "return all products with available number in stock at least param")
    @Parameter(name = "rating", description = "return all products with rating at least param")
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
            @ApiResponse(responseCode = "404", description = "product with given id does not exist")
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
            @ApiResponse(responseCode = "404", description = "product with given id does not exist"),
            @ApiResponse(responseCode = "409", description = "id of given product does not match id of product that should be edited")
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

    @PatchMapping(value = "/{id}")
    @Operation(description = "edit product with given id")
    @Parameter(description = "id of product that should be edited")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "product with given id does not exist")
    })
    public void patch(@PathVariable Long id, @RequestBody JsonNode data){
        Optional<Product> savedData = productService.readById(id);
        if(savedData.isEmpty()) throw new ResponseStatusException((HttpStatus.NOT_FOUND));

        Product toEdit = savedData.get();

        if(data.has("name")) toEdit.setName(data.get("name").asText());
        if(data.has("cost")) toEdit.setCost(data.get("cost").asInt());
        if(data.has("numberOfAvailable")) toEdit.setNumberOfAvailable(data.get("numberOfAvailable").asInt());

        productService.update(id,toEdit);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(description = "remove product with given id from stock")
    @Parameter(description = "id of product that should be removed")
    public void delete(@PathVariable Long id){
        productService.deleteById(id);
    }
}
