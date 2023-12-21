package cz.cvut.fit.tjv.czcClient.service;

import cz.cvut.fit.tjv.czcClient.api_client.ProductClient;
import cz.cvut.fit.tjv.czcClient.domain.Filters;
import cz.cvut.fit.tjv.czcClient.domain.Product;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductClient productClient;
    private Long currentProduct;

    public ProductService(ProductClient productClient) { this.productClient = productClient; }
    public boolean isCurrentProduct(){ return currentProduct!=null; }
    public void setCurrentProduct(Long id){
        currentProduct=id;
        productClient.setCurrentProduct(id);
    }

    public void create(Product data){ productClient.create(data); }
    public Optional<Product> getOne(){ return productClient.getOne(); }
    public Collection<Product> getAll(Filters filters){ return productClient.getAll(filters); }
    public void update(Product data){
        productClient.update(data);
    }
    public void delete(){ productClient.delete(); }
}