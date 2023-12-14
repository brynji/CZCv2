package cz.cvut.fit.tjv.czcv2.service;

import cz.cvut.fit.tjv.czcv2.domain.Buyer;
import cz.cvut.fit.tjv.czcv2.domain.Product;
import cz.cvut.fit.tjv.czcv2.domain.Review;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceIntegrationTest {
    @Autowired
    ProductService productService;
    @Autowired
    BuyerService buyerService;
    @Autowired
    ReviewService reviewService;

    @Transactional
    void updateRating(){
        Product p = new Product();
        p.setId(1L);
        Buyer b = new Buyer();
        b.setId(1L);
        Review r = new Review();
        r.setId(1L);
        p.setName("Kolo");
        b.setUsername("Dan");

        Product pDb = productService.create(p);
        Buyer bDb = buyerService.create(b);
        r.setProduct(pDb);
        r.setAuthor(bDb);
        r.setRating(50);

        System.out.println("Product rating without any reviews: "+pDb.getRating());
        reviewService.create(r);
        System.out.println("Product rating with one review: "+productService.readById(pDb.getId()).get().getRating());
        var revs = productService.readById(pDb.getId()).get().getReviews();
        System.out.println("Product reviews: "+revs);
    }
    @Test
    void test(){
        updateRating();
        System.out.println("Product rating with one review: "+productService.readById(1L).get().getRating());
        var revs = productService.readById(1L).get().getReviews();
        System.out.println("Product reviews: "+revs);
    }
}
