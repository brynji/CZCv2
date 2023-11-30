package cz.cvut.fit.tjv.czcClient.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Review {
    private Long id;
    private int rating;
    private String comment;
    private Long authorId;
    private Long productId;

    public Review() {
    }

    public Review(Long id, int rating, String comment, Long authorId, Long productId) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.authorId = authorId;
        this.productId = productId;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
