package cz.cvut.fit.tjv.czcv2.service;

import cz.cvut.fit.tjv.czcv2.domain.Review;
import cz.cvut.fit.tjv.czcv2.repository.ReviewRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public class ReviewServiceImpl extends CrudServiceImpl<Review,Long> implements ReviewService {
    private ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    protected CrudRepository<Review, Long> getRepository() {
        return reviewRepository;
    }
}
