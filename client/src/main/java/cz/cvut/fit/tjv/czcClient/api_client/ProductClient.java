package cz.cvut.fit.tjv.czcClient.api_client;

import cz.cvut.fit.tjv.czcClient.domain.Filters;
import cz.cvut.fit.tjv.czcClient.domain.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.*;

@Component
public class ProductClient {
    private final String baseUrl;
    private final RestClient productRestClient;
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

    public void create(Product data){
        productRestClient.post()
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    public Optional<Product> getOne(){
        try{
            return Optional.of(currentProductRestClient.get()
                    .retrieve().toEntity(Product.class).getBody());
        } catch (HttpClientErrorException.NotFound e){
            return Optional.empty();
        }
    }

    public Collection<Product> getAll(Filters filters){
        var res = productRestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("cost",filters.getCost())
                        .queryParam("available",filters.getNumberOfAvailable())
                        .queryParam("rating",filters.getRating())
                        .build())
                .accept(MediaType.APPLICATION_JSON).retrieve().toEntity(Product[].class).getBody();
        if(res!=null) return Arrays.asList(res);
        return new HashSet<Product>();
    }

    public void update(Product data){
        currentProductRestClient.put()
                .contentType(MediaType.APPLICATION_JSON)
                .body(data)
                .retrieve()
                .toBodilessEntity();
    }

    public void delete(){
        currentProductRestClient.delete()
                .retrieve()
                .toBodilessEntity();
    }
}
