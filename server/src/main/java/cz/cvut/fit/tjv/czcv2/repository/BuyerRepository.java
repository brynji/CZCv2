package cz.cvut.fit.tjv.czcv2.repository;

import cz.cvut.fit.tjv.czcv2.domain.Buyer;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface BuyerRepository extends CrudRepository<Buyer,Long>{
}
