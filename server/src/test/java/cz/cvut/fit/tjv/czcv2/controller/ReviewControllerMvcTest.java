package cz.cvut.fit.tjv.czcv2.controller;

import cz.cvut.fit.tjv.czcv2.domain.Buyer;
import cz.cvut.fit.tjv.czcv2.domain.Product;
import cz.cvut.fit.tjv.czcv2.domain.Review;
import cz.cvut.fit.tjv.czcv2.dto.ReviewDTO;
import cz.cvut.fit.tjv.czcv2.service.BuyerService;
import cz.cvut.fit.tjv.czcv2.service.ProductService;
import cz.cvut.fit.tjv.czcv2.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@WebMvcTest(ReviewController.class)
class ReviewControllerMvcTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ReviewService reviewService;
    @MockBean
    BuyerService buyerService;
    @MockBean
    ProductService productService;

    ReviewDTO review;
    Buyer buyer;
    Product product;

    @BeforeEach
    void setup(){
        review = new ReviewDTO();
        review.setId(1L);
        review.setAuthorId(2L);
        review.setProductId(3L);

        buyer = new Buyer();
        buyer.setId(2L);

        product = new Product();
        product.setId(3L);
    }

    @Test
    void create() throws Exception{
        Review rev = new Review(review.getId(),review.getRating(),review.getComment(),buyer,product);

        Mockito.when(buyerService.readById(buyer.getId())).thenReturn(Optional.of(buyer));
        Mockito.when(productService.readById(product.getId())).thenReturn(Optional.of(product));
        Mockito.when(reviewService.create(Mockito.any())).thenReturn(rev);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n\"id\" : 1,\n" +
                        "\"authorId\" : 2,\n" +
                        "\"productId\" : 3\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
        Mockito.verify(reviewService, Mockito.atLeastOnce()).create(Mockito.any());
    }
}