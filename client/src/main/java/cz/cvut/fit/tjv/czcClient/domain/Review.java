package cz.cvut.fit.tjv.czcClient.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Review {
    private Long id;
    private int rating;
    private String comment;
    @JsonIgnoreProperties({"boughtByMe","myReviews"})
    private Buyer author;
    @JsonIgnoreProperties("reviews")
    private Product product;

    @Override
    public String toString() {
        return author.getRealName()+": "+comment+"\n Rating: "+rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Buyer getAuthor() {
        return author;
    }

    public void setAuthor(Buyer author) {
        this.author = author;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
