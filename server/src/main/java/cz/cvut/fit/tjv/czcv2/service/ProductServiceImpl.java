package cz.cvut.fit.tjv.czcv2.service;

import cz.cvut.fit.tjv.czcv2.domain.Product;
import cz.cvut.fit.tjv.czcv2.repository.ProductRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ProductServiceImpl extends  CrudServiceImpl<Product,Long> implements ProductService {
    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Collection<Product> getAllWithFilters(int cost, int numberOfAvailable, double rating) {
        return productRepository.findByCostLessThanAndNumberOfAvailableGreaterThanEqualAndRatingGreaterThanEqual(cost, numberOfAvailable, rating);
    }

    @Override
    public double updateProductRating(Long id) {
        productRepository.updateProductRating(id);
        return productRepository.getProductRating(id);
    }

    @Override
    protected CrudRepository<Product, Long> getRepository() {
        return productRepository;
    }
}
