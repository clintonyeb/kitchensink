package org.jboss.as.quickstarts.kitchensink.service;

import org.jboss.as.quickstarts.kitchensink.model.Contest;
import org.jboss.as.quickstarts.kitchensink.model.Person;
import org.jboss.as.quickstarts.kitchensink.model.Team;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Set;

@Stateless
public class RegistrationService {

    @Inject
    private ContestRepository contestRepository;

    @Inject
    private TeamRepository teamRepository;

    public void registerTeamWithContest(Team team, Contest contest) {
        if (validateTeamWithContest(team, contest)) {
            contest.getTeams().add(team);
            team.setContest(contest);

            contestRepository.save(contest);
            teamRepository.save(team);
        } else {
            throw new ConstraintViolationException("Constraint violation");
        }
    }

    public void promoteTeam(Team team, Contest superContest) {
        if (validatePromotion(team, superContest)) {
            team.setContest(superContest);
            superContest.getTeams().add(team);

            contestRepository.save(superContest);
            teamRepository.save(team);
        } else {
            throw new ConstraintViolationException("Constraint violation");
        }
    }

    private boolean validateTeamWithContest(Team team, Contest contest) {

        // checks contest is writable
        if (! contest.getRegistrationAllowed()) return false;

        if (team.getCoach() == null) return false;

        // members are sets, distinction implied
        if (team.getMembership().size() != 3) return false;

        if (contest.getCapacity() >= contest.getTeams().size()) return false;

        for (Person person : team.getMembership()) {
            LocalDate birth = person.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (getAge(birth, LocalDate.now()) >= 24) return false;
        }

        if (! contest.getRegistrationAllowed()) {
            return false;
        }

        Set<Team> teams = contest.getTeams();

        for (Team contestTeam : teams) {
            for (Person person : team.getMembership()) {
                if (contestTeam.getMembership().contains(person)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean validatePromotion(Team team, Contest contest) {
        if (contest.getCapacity() >= contest.getTeams().size()) return false;
        if (! (team.getRank() >= 1 && team.getRank() <= 5)) return false;
        return validateTeamWithContest(team, contest);
    }

    private int getAge(LocalDate birthDate, LocalDate currentDate) {
        return Period.between(birthDate, currentDate).getYears();
    }
}

