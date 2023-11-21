package cz.cvut.fit.tjv.czcv2.repository;

import cz.cvut.fit.tjv.czcv2.domain.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ProductRepository extends CrudRepository<Product,Long> {

    Collection<Product> findByCostLessThan(int cost);
}
