package cz.cvut.fit.tjv.czcv2.domain;

import jakarta.persistence.*;

@Entity

public class Review implements EntityWithId<Long>{
    @Id
    @GeneratedValue
    private Long id;
    private int rating;
    private String comment;
    @ManyToOne
    private Buyer author;
    @ManyToOne
    private Product product;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id;}

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

    public Buyer getAuthor() { return author; }

    public void setAuthor(Buyer author) { this.author = author; }

    public Product getProduct() { return product; }

    public void setProduct(Product product) { this.product = product; }
}
