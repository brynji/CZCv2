package cz.cvut.fit.tjv.czcv2.repository;

import cz.cvut.fit.tjv.czcv2.domain.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReviewRepository extends CrudRepository<Review,Long> {
}