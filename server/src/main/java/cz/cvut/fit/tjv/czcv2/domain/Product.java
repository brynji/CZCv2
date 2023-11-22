package cz.cvut.fit.tjv.czcv2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.Collection;
import java.util.HashSet;

@Entity
public class Product implements EntityWithId<Long>{
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    private int cost;
    private int numberOfAvailable;
    private int rating = 0;
    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties("product")
    private Collection<Review> reviews = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfAvailable() {
        return numberOfAvailable;
    }

    public void setNumberOfAvailable(int numberOfAvailable) {
        this.numberOfAvailable = numberOfAvailable;
    }

    public int getRating() { return rating; }

    public void setRating(int rating) { this.rating = rating; }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Collection<Review> getReviews() { return reviews; }

    public void setReviews(Collection<Review> reviews) { this.reviews = reviews; }
}