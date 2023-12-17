package cz.cvut.fit.tjv.czcv2.service;

import cz.cvut.fit.tjv.czcv2.domain.Buyer;
import cz.cvut.fit.tjv.czcv2.domain.Product;
import cz.cvut.fit.tjv.czcv2.domain.Review;
import cz.cvut.fit.tjv.czcv2.repository.ProductRepository;
import cz.cvut.fit.tjv.czcv2.repository.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
class ReviewServiceUnitTest {
    @Autowired
    ReviewService reviewService;
    @MockBean
    ReviewRepository reviewRepository;
    @MockBean
    ProductRepository productRepository;
    Buyer buyer;
    Product product;
    Review review;

    @BeforeEach
    void setUp(){
        buyer = new Buyer();
        product = new Product();
        review = new Review();

        buyer.setId(1L);
        buyer.setUsername("TestBuyer");
        buyer.setAddress("Praha");
        buyer.setRealName("Dan");

        product.setId(2L);
        product.setName("TestProduct");
        product.setCost(100);
        product.setNumberOfAvailable(1);
        product.setRating(0);

        review.setId(3L);
        review.setComment("TestReview");
        review.setRating(10);
        review.setAuthor(buyer);
        review.setProduct(product);

        Mockito.when(
                reviewRepository.findById(review.getId())
        ).thenReturn(Optional.of(review));
    }

    @Test
    void createOk() {
        Mockito.doAnswer(a -> {
            product.setRating(review.getRating());
            return null;})
                .when(productRepository).updateProductRating(product.getId());

        reviewService.create(review);

        Assertions.assertEquals(product.getRating(),review.getRating());
        Mockito.verify(reviewRepository,Mockito.atLeastOnce()).save(review);
        Mockito.verify(productRepository,Mockito.atLeastOnce()).updateProductRating(product.getId());
    }

    @Test
    void reviewAlreadyPresentShouldThrow(){
        reviewService.create(review);
        Mockito.when(reviewRepository.existsById(review.getId())).thenReturn(true);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> reviewService.create(review));
    }

    @Test
    void authorAlreadyHasReviewOnThatProductShouldThrow(){
        reviewService.create(review);
        buyer.getMyReviews().add(review);
        Mockito.when(reviewRepository.existsById(review.getId())).thenReturn(true);

        review.setId(5L);
        review.setComment("Another Review");
        review.setRating(56);

        Assertions.assertThrows(UnsupportedOperationException.class,
                () -> reviewService.create(review));
    }
}