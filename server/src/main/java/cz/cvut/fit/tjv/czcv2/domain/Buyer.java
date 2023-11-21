package cz.cvut.fit.tjv.czcv2.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class Buyer implements EntityWithId<Long>{
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String address;
    private String realName;
    @ManyToMany
    private Collection<Product> boughtByMe = new ArrayList<>();
    @OneToMany(mappedBy = "author")
    private Collection<Review> myReviews = new HashSet<>();

    @Override
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
