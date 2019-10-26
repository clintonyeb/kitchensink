package org.jboss.as.quickstarts.kitchensink.data;

import org.jboss.as.quickstarts.kitchensink.model.Contest;
import org.jboss.as.quickstarts.kitchensink.model.Member;
import org.jboss.as.quickstarts.kitchensink.model.Team;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
public class TeamListProducer {
    @Inject
    private TeamRepository teamRepository;

    @Inject
    private ContestRepository contestRepository;

    private List<Team> teams;

    private List<Contest> contests;

    @Produces
    @Named
    public List<Team> getTeams() {
        return teams;
    }

    @Produces
    @Named
    public List<Contest> getContests() {
        return contests;
    }

    public void onTeamListChanged(@Observes(notifyObserver = Reception.ALWAYS) final Team team) {
        retrieveAllTeamsOrderedByName();
    }

    public void onContestListChanged(@Observes(notifyObserver = Reception.ALWAYS) final Contest contest) {
       retrieveAllContestsOrderedByName();
    }


    public void retrieveAllTeamsOrderedByName() {
        teams = teamRepository.findAllOrderedByName();
    }

    public void retrieveAllContestsOrderedByName() {
        contests = contestRepository.findAll();
    }

    @PostConstruct
    public void updateAll() {
        teams = teamRepository.findAllOrderedByName();
        contests = contestRepository.findAll();
    }
}
