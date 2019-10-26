package org.jboss.as.quickstarts.kitchensink.data;

import org.jboss.as.quickstarts.kitchensink.model.Team;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class TeamRepository{
    @Inject
    private EntityManager em;

    public Team findById(Long id) {
        return em.find(Team.class, id);
    }

    public Team findByName(String name) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Team> criteria = cb.createQuery(Team.class);
        Root<Team> team = criteria.from(Team.class);
        criteria.select(team).where(cb.equal(team.get("name"), name));
        return em.createQuery(criteria).getSingleResult();
    }

    public List<Team> findAllOrderedByName() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Team> criteria = cb.createQuery(Team.class);
        Root<Team> team = criteria.from(Team.class);
        criteria.select(team).orderBy(cb.asc(team.get("name")));
        return em.createQuery(criteria).getResultList();
    }

    public List<Team> findAllTeamsLikeOrderedByName(String search) {
        search = search.toLowerCase();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Team> criteria = cb.createQuery(Team.class);
        Root<Team> team = criteria.from(Team.class);

        Expression<String> path = cb.trim(cb.lower(team.get("name")));

        criteria.select(team).where(cb.like(path, "%" + search + "%")).orderBy(cb.asc(team.get("name")));
        return em.createQuery(criteria).getResultList();
    }

    public void updateTeam(long id, Team.State teamState) {
//        Team t = em.find(Team.class, id);
//        System.out.println(teamState + " team state hererescss ");
//        t.setState(teamState);
//        return em.merge(t);
    }

    @Transactional
    public Team save(Team t) {
        return em.merge(t);
    }
}
