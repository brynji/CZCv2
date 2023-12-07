package cz.cvut.fit.tjv.czcClient.service;

import cz.cvut.fit.tjv.czcClient.api_client.ReviewClient;
import cz.cvut.fit.tjv.czcClient.domain.Review;
import cz.cvut.fit.tjv.czcClient.domain.ReviewDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ReviewService {
    private ReviewClient reviewClient;
    private Long currentReview;

    public ReviewService(ReviewClient reviewClient) { this.reviewClient = reviewClient; }

    public boolean isCurrentReview(){ return currentReview!=null; }

    public void setCurrentReview(Long id){
        currentReview=id;
        reviewClient.setCurrentReview(id);
    }

    public void create(ReviewDto data){ reviewClient.create(data); }
    public Optional<Review> getOne(){ return reviewClient.getOne(); }
    public Collection<Review> getAll(){ return reviewClient.getAll(); }
    public void update(ReviewDto data){ reviewClient.update(data); }
    public void delete(){ reviewClient.delete(); }
}
