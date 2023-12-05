package cz.cvut.fit.tjv.czcv2.repository;

import cz.cvut.fit.tjv.czcv2.domain.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {
    Collection<Product> findByCostLessThanEqualAndNumberOfAvailableGreaterThanEqualAndRatingGreaterThanEqual(int cost, int numberOfAvailable, double rating);
    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.rating = COALESCE((SELECT AVG(r.rating) FROM Review r WHERE r.product.id = :id),0) WHERE p.id = :id")
    void updateProductRating(Long id);

    @Query("SELECT p.rating FROM Product p WHERE p.id = :id")
    double getProductRating(Long id);
}
