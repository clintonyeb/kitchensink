package org.jboss.as.quickstarts.kitchensink.service;

import org.jboss.as.quickstarts.kitchensink.data.ContestRepository;
import org.jboss.as.quickstarts.kitchensink.data.TeamRepository;
import org.jboss.as.quickstarts.kitchensink.model.Contest;
import org.jboss.as.quickstarts.kitchensink.model.Team;

import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class TeamService {
    @Inject
    private TeamRepository teamRepository;

    @Inject
    private ContestRepository contestRepository;


    public List<Team> doSearch(String param) {
        return  teamRepository.findAllTeamsLikeOrderedByName(param);
    }

    public void saveContest(Contest contest) {
        contestRepository.save(contest);
    }
}
