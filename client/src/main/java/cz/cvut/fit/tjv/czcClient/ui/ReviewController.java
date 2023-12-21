package cz.cvut.fit.tjv.czcClient.ui;

import cz.cvut.fit.tjv.czcClient.domain.Product;
import cz.cvut.fit.tjv.czcClient.domain.Review;
import cz.cvut.fit.tjv.czcClient.domain.ReviewDto;
import cz.cvut.fit.tjv.czcClient.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) { this.reviewService = reviewService; }

    @PostMapping("/add")
    public String post(Model model, @ModelAttribute ReviewDto review){
        try{
            reviewService.create(review);
        } catch (HttpClientErrorException.NotFound e){
            model.addAttribute("error","Invalid Author ID");
            model.addAttribute("newReview",review);
            return "addReview";
        } catch (HttpClientErrorException.Conflict e){
            model.addAttribute("error","Author already posted review to the product");
            model.addAttribute("newReview",review);
            return "addReview";
        }
        return "redirect:/products/"+review.getProductId();
    }

    @PostMapping("/edit")
    public String editInDb(Model model, @ModelAttribute ReviewDto review){
        reviewService.setCurrentReview(review.getId());
        reviewService.update(review);
        return "redirect:/products/"+review.getProductId();
    }

    @GetMapping("/{id}")
    public String details(Model model, @PathVariable Long id){
        reviewService.setCurrentReview(id);
        Optional<Review> review = reviewService.getOne();
        model.addAttribute("currentReview",review.get());
        return "reviewDetails";
    }

    @GetMapping("/add/{id}")
    public String add(Model model, @PathVariable Long id){
        var r = new ReviewDto();
        r.setProductId(id);
        model.addAttribute("newReview",r);
        return "addReview";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable Long id){
        reviewService.setCurrentReview(id);
        ReviewDto review = new ReviewDto(reviewService.getOne().get());
        model.addAttribute("toEditReview",review);
        return "editReview";
    }

    @GetMapping("/{id}/delete")
    public String delete(Model model, @PathVariable Long id){
        reviewService.setCurrentReview(id);
        Long returnId = reviewService.getOne().get().getProduct().getId();
        reviewService.delete();
        return "redirect:/products/"+returnId;
    }

}
