package cz.cvut.fit.tjv.czcClient.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collection;
import java.util.HashSet;

public class Product {
    private Long id = 0L;
    private String name;
    private int cost = 0;
    private int numberOfAvailable;
    private double rating = 0;
    @JsonIgnoreProperties("product")
    private Collection<Review> reviews = new HashSet<>();

    public Product() {
    }

    public Product(Long id, String name, int cost, int numberOfAvailable, double rating, Collection<Review> reviews) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.numberOfAvailable = numberOfAvailable;
        this.rating = rating;
        this.reviews = reviews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getNumberOfAvailable() {
        return numberOfAvailable;
    }

    public void setNumberOfAvailable(int numberOfAvailable) {
        this.numberOfAvailable = numberOfAvailable;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Collection<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return getName();
    }
}
