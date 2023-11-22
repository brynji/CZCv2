package cz.cvut.fit.tjv.czcv2.service;

import cz.cvut.fit.tjv.czcv2.domain.Product;;import java.util.Collection;

public interface ProductService extends CrudService<Product,Long> {
    Collection<Product> getAllWithCostLessThan (int cost);
    Collection<Product> getAllWithFilters(int cost, int numberOfAvaible, int rating);
}
