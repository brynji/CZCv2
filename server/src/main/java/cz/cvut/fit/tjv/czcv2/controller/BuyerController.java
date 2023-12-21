package cz.cvut.fit.tjv.czcv2.controller;

import cz.cvut.fit.tjv.czcv2.domain.Buyer;
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
@RequestMapping(value = "/buyer")
public class BuyerController {
    private final BuyerService buyerService;
    private final ProductService productService;

    public BuyerController(BuyerService buyerService, ProductService productService) {
        this.buyerService = buyerService;
        this.productService = productService;
    }

    @PostMapping
    @Operation(description = "register new user")
    public Buyer create(@RequestBody Buyer data){
        data.setId(0L);
        return buyerService.create(data);
    }

    @PostMapping(value = "/{id}/boughtBy/{productId}")
    @Operation(description = "add new bought product to user with given id")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "user or product with given id does not exist", content=@Content)
    })
    public void addBought(@PathVariable Long id, @PathVariable Long productId){
        Optional<Product> toAdd = productService.readById(productId);
        Optional<Buyer> buyer = buyerService.readById(id);
        if(buyer.isEmpty() || toAdd.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Buyer data = buyer.get();
        data.getBoughtByMe().add(toAdd.get());
        buyerService.update(id,data);
    }

    @GetMapping
    @Operation(description = "return all registered users")
    @ApiResponses(value = { @ApiResponse( content = {
            @Content( mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Buyer.class)))})})
    public Iterable<Buyer> get(){
        return buyerService.readAll();
    }

    @GetMapping(value = "/{id}")
    @Operation(description = "return user with given id")
    @Parameter(description = "id of user that should be returned")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "user with given id does not exist", content=@Content)
    })
    public Buyer get(@PathVariable Long id){
        Optional<Buyer> res = buyerService.readById(id);
        if(res.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return res.get();
    }

    @PutMapping(value = "/{id}")
    @Operation(description = "overwrite user with given id")
    @Parameter(description = "id of user that should be edited")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "user with given id does not exist", content=@Content),
            @ApiResponse(responseCode = "409", description = "id of given user does not match id of user that should be edited", content=@Content)
    })
    public void edit(@PathVariable Long id, @RequestBody Buyer data){
        try{
            buyerService.update(id,data);
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping(value = "/{id}")
    @Operation(description = "delete user with given id")
    @Parameter(description = "id of user that should be removed")
    public void delete(@PathVariable Long id){
        buyerService.deleteById(id);
    }
}
