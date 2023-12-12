package cz.cvut.fit.tjv.czcv2.service;

import cz.cvut.fit.tjv.czcv2.domain.Review;
import cz.cvut.fit.tjv.czcv2.repository.BuyerRepository;
import cz.cvut.fit.tjv.czcv2.repository.ProductRepository;
import cz.cvut.fit.tjv.czcv2.repository.ReviewRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.management.BadAttributeValueExpException;
import java.nio.file.FileAlreadyExistsException;

@Component
public class ReviewServiceImpl extends CrudServiceImpl<Review,Long> implements ReviewService {
    private ReviewRepository reviewRepository;
    private ProductRepository productRepository;
    private BuyerRepository buyerRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository, BuyerRepository buyerRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.buyerRepository = buyerRepository;
    }

    @Override
    protected CrudRepository<Review, Long> getRepository() {
        return reviewRepository;
    }

    @Override
    public Review create(Review e) {
        if (getRepository().existsById(e.getId())){
            throw new IllegalArgumentException();
        }
        var reviews = e.getAuthor().getMyReviews();
        if(reviews.stream().anyMatch(x -> x.getProduct().getId().equals(e.getProduct().getId()))){
            throw new UnsupportedOperationException();
        }

        var res = getRepository().save(e);
        productRepository.updateProductRating(e.getProduct().getId());
        return res;
    }

    @Override
    public void update(Long id, Review e) {
        if(!getRepository().existsById(id)) throw new IllegalArgumentException();
        if(!id.equals(e.getId())) throw new IllegalStateException();
        getRepository().save(e);

        productRepository.updateProductRating(e.getProduct().getId());
    }

    @Override
    public void deleteById(Long id) {
        var review = getRepository().findById(id);
        getRepository().deleteById(id);
        if(review.isPresent()) productRepository.updateProductRating(review.get().getProduct().getId());
    }
}
