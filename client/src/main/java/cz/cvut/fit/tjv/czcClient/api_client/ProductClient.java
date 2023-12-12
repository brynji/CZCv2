package cz.cvut.fit.tjv.czcClient.api_client;

import cz.cvut.fit.tjv.czcClient.domain.Filters;
import cz.cvut.fit.tjv.czcClient.domain.Product;
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
public class ProductClient {
    private String baseUrl;
    private RestClient productRestClient;
    private RestClient currentProductRestClient;

    public ProductClient(@Value("${server.url}") String baseUrl) {
        this.baseUrl = baseUrl;
        productRestClient = RestClient.create(baseUrl+"/product");
    }

    public void setCurrentProduct(Long id){
        currentProductRestClient = RestClient.builder()
                .baseUrl(baseUrl+"/product/{id}")
                .defaultUriVariables(Map.of("id",id))
                .build();
    }

    public String getToken(){
        var securityCtx = SecurityContextHolder.getContext();
        var principal = securityCtx.getAuthentication().getPrincipal();
        OAuth2Token token = ((OidcUser)principal).getIdToken();
        return token.getTokenValue();
    }

    public void create(Product data){
        productRestClient.post()
                .header("Authorization", "Bearer " + getToken())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    public Optional<Product> getOne(){
        try{
            return Optional.of(currentProductRestClient.get()
                    .header("Authorization", "Bearer " + getToken())
                    .retrieve().toEntity(Product.class).getBody());
        } catch (HttpClientErrorException.NotFound e){
            return Optional.empty();
        }
    }

    public Collection<Product> getAll(Filters filters){
        var securityCtx = SecurityContextHolder.getContext();
        var principal = securityCtx.getAuthentication().getPrincipal();
        OAuth2Token token = ((OidcUser)principal).getIdToken();

        var res = productRestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("cost",filters.getCost())
                        .queryParam("available",filters.getNumberOfAvailable())
                        .queryParam("rating",filters.getRating())
                        .build())
                .header("Authorization", "Bearer " + token.getTokenValue())
                .accept(MediaType.APPLICATION_JSON).retrieve().toEntity(Product[].class).getBody();
        if(res!=null) return Arrays.asList(res);
        return new HashSet<Product>();
    }

    public void update(Product data){
        currentProductRestClient.put()
                .header("Authorization", "Bearer " + getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete(){
        currentProductRestClient.delete()
                .header("Authorization", "Bearer " + getToken())
                .retrieve()
                .toBodilessEntity();
    }
}
