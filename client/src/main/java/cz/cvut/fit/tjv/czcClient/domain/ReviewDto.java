package cz.cvut.fit.tjv.czcClient.domain;

public class ReviewDto {
    private Long id;
    private int rating;
    private String comment;
    private Long authorId;
    private Long productId;

    public ReviewDto() {
    }

    public ReviewDto(Long id, int rating, String comment, Long authorId, Long productId) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.authorId = authorId;
        this.productId = productId;
    }

    public ReviewDto(Review rev){
        this.id = rev.getId();
        this.rating = rev.getRating();
        this.comment = rev.getComment();
        this.authorId = rev.getAuthor().getId();
        this.productId = rev.getProduct().getId();
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
