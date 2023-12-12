package cz.cvut.fit.tjv.czcClient.api_client;

import cz.cvut.fit.tjv.czcClient.domain.Review;
import cz.cvut.fit.tjv.czcClient.domain.ReviewDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.*;

@Component
public class ReviewClient {
    private String baseUrl;
    private RestClient reviewRestClient;
    private RestClient currentReviewRestClient;

    public ReviewClient(@Value("${server.url}") String baseUrl) {
        this.baseUrl = baseUrl;
        reviewRestClient = RestClient.create(baseUrl+"/review");
    }

    public String getToken(){
        var securityCtx = SecurityContextHolder.getContext();
        var principal = securityCtx.getAuthentication().getPrincipal();
        OAuth2Token token = ((OidcUser)principal).getIdToken();
        return token.getTokenValue();
    }

    public void setCurrentReview(Long id){
        currentReviewRestClient = RestClient.builder()
                .baseUrl(baseUrl+"/review/{id}")
                .defaultUriVariables(Map.of("id",id))
                .build();
    }

    public void create(ReviewDto data){
        reviewRestClient.post()
                .header("Authorization", "Bearer " + getToken())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    public Optional<Review> getOne(){
        try{
            return Optional.of(currentReviewRestClient.get()
                    .header("Authorization", "Bearer " + getToken())
                    .accept(MediaType.APPLICATION_JSON).retrieve().toEntity(Review.class).getBody());
        } catch (HttpClientErrorException.NotFound e){
            return Optional.empty();
        }
    }

    public Collection<Review> getAll(){
        var res = reviewRestClient.get()
                .header("Authorization", "Bearer " + getToken())
                .accept(MediaType.APPLICATION_JSON).retrieve().toEntity(Review[].class).getBody();
        if(res!=null) return Arrays.asList(res);
        return new HashSet<Review>();
    }

    public void update(ReviewDto data){
        currentReviewRestClient.put()
                .header("Authorization", "Bearer " + getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete(){
        currentReviewRestClient.delete()
                .header("Authorization", "Bearer " + getToken())
                .retrieve()
                .toBodilessEntity();
    }
}
