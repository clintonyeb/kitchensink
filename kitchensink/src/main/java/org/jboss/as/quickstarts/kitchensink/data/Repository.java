package org.jboss.as.quickstarts.kitchensink.data;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.List;

public class Repository implements EntityRepository {

    @Inject
    private EntityManager em;

    @Override
    public Object save(Object entity) {
        return em.merge(entity);
    }

    @Override
    public void remove(Object entity) {

    }

    @Override
    public void refresh(Object entity) {

    }

    @Override
    public void flush() {

    }

    @Override
    public Object findBy(Serializable primaryKey) {
        return null;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public Long countLike(Object example, SingularAttribute[] attributes) {
        return null;
    }

    @Override
    public Long count(Object example, SingularAttribute[] attributes) {
        return null;
    }

    @Override
    public List findByLike(Object example, SingularAttribute[] attributes) {
        return null;
    }

    @Override
    public List findBy(Object example, SingularAttribute[] attributes) {
        return null;
    }
}
