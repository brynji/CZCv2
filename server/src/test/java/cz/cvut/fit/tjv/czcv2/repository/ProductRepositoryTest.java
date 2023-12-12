package cz.cvut.fit.tjv.czcv2.repository;

import cz.cvut.fit.tjv.czcv2.domain.Buyer;
import cz.cvut.fit.tjv.czcv2.domain.Product;
import cz.cvut.fit.tjv.czcv2.domain.Review;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    //Test if updateProductRating correctly calculates rating
    /*@Test
    void updateProductRating(){
        Product product = new Product();
        Buyer buyer = new Buyer();
        Review review = new Review();
        Review review1 = new Review();
        Review review2 = new Review();

        product.setName("test");
        review.setRating(100);
        review1.setRating(50);
        review2.setRating(0);

        Long id = productRepository.save(product).getId();
        Buyer buyerInDb = buyerRepository.save(buyer);

        Assertions.assertEquals(0,productRepository.findById(id).get().getRating());

        review.setProduct(productRepository.findById(id).get());
        review.setAuthor(buyerInDb);
        System.out.println(reviewRepository.save(review).getProduct());
        System.out.println(buyerRepository.findById(buyerInDb.getId()).get().getMyReviews());
        productRepository.updateProductRating(id);
        System.out.println(productRepository.findById(id).get());
        Assertions.assertEquals(100,productRepository.findById(id).get().getRating());

    }*/

    @Test
    void idk(){
        Buyer buyer = new Buyer();
        buyer.setUsername("xd"); buyer.setRealName("Dan"); buyer.setAddress("asd315");
        Product product = new Product();
        product.setName("aaaa"); product.setCost(20); product.setNumberOfAvailable(3);
        var p =productRepository.save(product);
        var b =buyerRepository.save(buyer);

        Review review = new Review();
        review.setProduct(p); review.setAuthor(b); review.setRating(50); review.setComment("Best");
        var r = reviewRepository.save(review);
        System.out.println("Buyer reviews: "+buyerRepository.findById(b.getId()).get().getMyReviews().toString());
        System.out.println("Product reviews: "+productRepository.findById(p.getId()).get().getReviews());
        var rdb = reviewRepository.findById(r.getId()).get();
        System.out.println("Review: Product "+rdb.getProduct().getName()+", Author "+rdb.getAuthor().getUsername()+", Author reviews "+rdb.getAuthor().getMyReviews().toString());
        System.out.println("All authors: "+buyerRepository.findAll().toString());
    }
}
