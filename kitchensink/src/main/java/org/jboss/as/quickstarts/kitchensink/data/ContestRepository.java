package org.jboss.as.quickstarts.kitchensink.data;

import org.jboss.as.quickstarts.kitchensink.model.Contest;
import org.jboss.as.quickstarts.kitchensink.model.Team;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@ApplicationScoped
public class ContestRepository{
    @Inject
    private EntityManager em;

    public Contest save(Contest c) {
        return em.merge(c);
    }

    public List<Contest> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Contest> criteria = cb.createQuery(Contest.class);
        Root<Contest> contest = criteria.from(Contest.class);
        criteria.select(contest);
        return em.createQuery(criteria).getResultList();
    }

    public Contest findById(Long id) {
        return em.find(Contest.class, id);
    }
}
