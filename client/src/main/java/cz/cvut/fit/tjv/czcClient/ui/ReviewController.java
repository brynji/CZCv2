package cz.cvut.fit.tjv.czcClient.ui;

import cz.cvut.fit.tjv.czcClient.domain.Review;
import cz.cvut.fit.tjv.czcClient.domain.ReviewDto;
import cz.cvut.fit.tjv.czcClient.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) { this.reviewService = reviewService; }

    @GetMapping("/add/{id}")
    public String add(Model model, @PathVariable Long id){
        var r = new ReviewDto();
        r.setProductId(id);
        model.addAttribute("newReview",r);
        return "addReview";
    }

    @PostMapping("/add")
    public String post(Model model, @ModelAttribute ReviewDto review){
        reviewService.create(review);
        return "redirect:/products";
    }
}
