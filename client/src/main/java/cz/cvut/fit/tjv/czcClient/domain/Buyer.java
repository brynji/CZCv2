package cz.cvut.fit.tjv.czcClient.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Buyer{
    private Long id;
    private String username;
    private String address;
    private String realName;
    private Collection<Product> boughtByMe = new ArrayList<>();
    @JsonIgnoreProperties("author")
    private Collection<Review> myReviews = new HashSet<>();

    public Buyer() {
    }

    public Buyer(Long id, String username, String address, String realName, Collection<Product> boughtByMe, Collection<Review> myReviews) {
        this.id = id;
        this.username = username;
        this.address = address;
        this.realName = realName;
        this.boughtByMe = boughtByMe;
        this.myReviews = myReviews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Collection<Product> getBoughtByMe() {
        return boughtByMe;
    }

    public void setBoughtByMe(Collection<Product> boughtByMe) {
        this.boughtByMe = boughtByMe;
    }

    public Collection<Review> getMyReviews() {
        return myReviews;
    }

    public void setMyReviews(Collection<Review> myReviews) {
        this.myReviews = myReviews;
    }
}
