package cz.cvut.fit.tjv.czcClient.api_client;

import cz.cvut.fit.tjv.czcClient.domain.Buyer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.*;

@Component
public class BuyerClient {
    private String baseUrl;
    private RestClient buyerRestClient;
    private RestClient currentBuyerRestClient;
    public BuyerClient(@Value("${server.url}") String baseUrl) {
        this.baseUrl = baseUrl;
        buyerRestClient = RestClient.create(baseUrl+"/buyer");
    }

    public void setCurrentBuyer(Long id){
        currentBuyerRestClient = RestClient.builder()
                .baseUrl(baseUrl+"/buyer/{id}")
                .defaultUriVariables(Map.of("id",id))
                .build();
    }

    public String getToken(){
        var securityCtx = SecurityContextHolder.getContext();
        var principal = securityCtx.getAuthentication().getPrincipal();
        OAuth2Token token = ((OidcUser)principal).getIdToken();
        return token.getTokenValue();
    }

    public void create(Buyer data){
        buyerRestClient.post()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    public Optional<Buyer> getOne(){
        try{
            return Optional.of(currentBuyerRestClient.get()
                    .header("Authorization", "Bearer " + getToken())
                    .retrieve().toEntity(Buyer.class).getBody());
        } catch (HttpClientErrorException.NotFound e){
            return Optional.empty();
        }
    }

    public Collection<Buyer> getAll(){

        var res = buyerRestClient.get()
                .header("Authorization", "Bearer " + getToken())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve().toEntity(Buyer[].class)
                .getBody();
        if(res!=null) return Arrays.asList(res);
        return new HashSet<Buyer>();
    }

    public void update(Buyer data){
        currentBuyerRestClient.put()
                .header("Authorization", "Bearer " + getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete(){
        currentBuyerRestClient.delete()
                .header("Authorization", "Bearer " + getToken())
                .retrieve()
                .toBodilessEntity();
    }
}
