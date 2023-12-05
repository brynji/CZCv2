package cz.cvut.fit.tjv.czcClient.ui;

import cz.cvut.fit.tjv.czcClient.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) { this.reviewService = reviewService; }
}
