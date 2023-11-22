package cz.cvut.fit.tjv.czcv2.dto;

import cz.cvut.fit.tjv.czcv2.domain.EntityWithId;

public class ReviewDTO implements EntityWithId<Long> {
    private Long id;
    private int rating;
    private String comment;
    private Long authorId;
    private Long productId;

    @Override
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
