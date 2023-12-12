package cz.cvut.fit.tjv.czcv2.service;

import cz.cvut.fit.tjv.czcv2.domain.Review;

public interface ReviewService extends CrudService<Review,Long> {
    @Override
    Review create(Review e);

    @Override
    void update(Long id, Review e);

    @Override
    void deleteById(Long id);
}
