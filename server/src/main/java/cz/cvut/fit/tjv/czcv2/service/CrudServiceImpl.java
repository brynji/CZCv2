package cz.cvut.fit.tjv.czcv2.service;

import cz.cvut.fit.tjv.czcv2.domain.EntityWithId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

public abstract class CrudServiceImpl<T extends EntityWithId<ID>, ID> implements CrudService<T, ID> {
    @Override
    public T create(T e) {
        if (getRepository().existsById(e.getId())){
            throw new IllegalArgumentException();
        }
        return getRepository().save(e);
    }

    @Override
    public Optional<T> readById(ID id) {
        return getRepository().findById(id);
    }

    @Override
    public Iterable<T> readAll() {
        return getRepository().findAll();
    }

    @Override
    public void update(ID id, T e) {
        if(!getRepository().existsById(id)) throw new IllegalArgumentException();
        if(!id.equals(e.getId())) throw new IllegalStateException();
        getRepository().save(e);
    }

    @Override
    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    protected abstract CrudRepository<T, ID> getRepository();
}
