package cz.cvut.fit.tjv.czcv2.repository;

import cz.cvut.fit.tjv.czcv2.domain.Buyer;
import cz.cvut.fit.tjv.czcv2.domain.Product;
import cz.cvut.fit.tjv.czcv2.domain.Review;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void updateProductRating(){
        Product product  = new Product(); product.setId(1L);  product.setCost(10);  product.setNumberOfAvailable(0);  product.setRating(0);
        Buyer buyer = new Buyer(); buyer.setId(1L);
        Review review = new Review();

        var pr = productRepository.save(product);
        var bu = buyerRepository.save(buyer);
        Long id = pr.getId();

        review.setProduct(pr);
        review.setAuthor(bu);
        review.setId(1L);

        productRepository.updateProductRating(pr.getId());
        Assertions.assertEquals(0,productRepository.getProductRating(id));

        review.setRating(50);
        reviewRepository.save(review);
        productRepository.updateProductRating(pr.getId());
        Assertions.assertEquals(150,productRepository.getProductRating(id));

        review.setId(2L);
        review.setRating(100);
        reviewRepository.save(review);
        productRepository.updateProductRating(pr.getId());
        Assertions.assertEquals(75,productRepository.getProductRating(id));

        review.setId(3L);
        review.setRating(0);
        reviewRepository.save(review);
        productRepository.updateProductRating(pr.getId());
        Assertions.assertEquals(50,productRepository.getProductRating(id));

        reviewRepository.deleteAll();
        productRepository.updateProductRating(pr.getId());
        Assertions.assertEquals(0,productRepository.getProductRating(id));
    }
}
