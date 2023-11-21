package cz.cvut.fit.tjv.czcv2.controller;

import com.fasterxml.jackson.databind.JsonNode;
import cz.cvut.fit.tjv.czcv2.domain.Review;
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

    public ReviewController(ReviewService reviewService) { this.reviewService = reviewService; }

    @PostMapping
    @Operation(description = "Create new review")
    public Review create(@RequestBody Review data){
        data.setId(0L);
        return reviewService.create(data);
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
        if(res.isPresent()) return res.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
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
    }

    @DeleteMapping(value = "/{id}")
    @Operation(description = "delete review with given id")
    @Parameter(description = "id of review that should be removed")
    public void delete(@PathVariable Long id){
        reviewService.deleteById(id);
    }

}
