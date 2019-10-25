package org.jboss.as.quickstarts.kitchensink.data;

import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.List;

public interface EntityRepository<E, PK extends Serializable>
{

    E save(E entity);

    void remove(E entity);

    void refresh(E entity);

    void flush();

    E findBy(PK primaryKey);

    List<E> findAll();

    List<E> findBy(E example, SingularAttribute<E, ?>... attributes);

    List<E> findByLike(E example, SingularAttribute<E, ?>... attributes);

    Long count();

    Long count(E example, SingularAttribute<E, ?>... attributes);

    Long countLike(E example, SingularAttribute<E, ?>... attributes);

}