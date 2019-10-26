package org.jboss.as.quickstarts.kitchensink.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.quickstarts.kitchensink.data.ContestRepository;
import org.jboss.as.quickstarts.kitchensink.data.PersonRepository;
import org.jboss.as.quickstarts.kitchensink.data.TeamRepository;
import org.jboss.as.quickstarts.kitchensink.model.Contest;
import org.jboss.as.quickstarts.kitchensink.model.Person;
import org.jboss.as.quickstarts.kitchensink.model.Team;
import org.jboss.as.quickstarts.kitchensink.service.RegistrationService;
import org.jboss.as.quickstarts.kitchensink.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.Date;

@RunWith(Arquillian.class)
public class RegistrationServiceTest {
    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Resources.class,
                        TeamRepository.class,
                        Team.class,
                        Contest.class,
                        Person.class,
                        RegistrationService.class,
                        ContestRepository.class,
                        PersonRepository.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml");
    }

    @Inject
    private ContestRepository contestRepository;

    @Inject
    private TeamRepository teamRepository;

    @Inject
    private PersonRepository personRepository;

    @Test
    public void registerTeamWithContest() {
    }

    @Test
    public void promoteTeam() {
    }

    /**
     * Test for Successful team promotion
     */
    @Test
    @Ignore
    public void promoteTeam_2() {
        Contest contest = new Contest();
        contest.setCapacity(3);

        for (int t = 0; t < 3; t++) {
            Team team = new Team();
            team.setName("Sharks" + team);

            for (int i = 0; i < 4; i++) {
                Person p = new Person();
                p.setName("person");
                p.setEmail("per" + i + "@gmail.com");
                p.setUniversity("University" + i);
                p.setBirthDate(new Date());

                p = personRepository.save(p);

                if (i == 0) {
                    team.setCoach(p);
                } else {
                    team.getMembership().add(p);
                }

            }

            team = teamRepository.save(team);

            contest.getTeams().add(team);

            contest = contestRepository.save(contest);
        }
        assert (contest.getTeams().size() == 3);
    }

    /**
     * Contest capacity is 3 but trying to add a 4 team
     */
    @Test
    public void contestCapacity() {
        Contest contest = new Contest();
        contest.setCapacity(3);
        for (int t = 0; t < 3; t++) {
            Team team = new Team();
            team.setName("Sharks" + team);
            for (int i = 0; i < 4; i++) {
                Person p = new Person();
                p.setName(team + "person" + i + 1);
                p.setEmail("per" + i + "@gmail.com");
                p.setUniversity("University" + i);
                p.setUniversity("");
                if (i == 0) {
                    team.setCoach(p);
                } else {
                    team.getMembership().add(p);
                }
            }
            contest.getTeams().add(team);
        }
    }

    /**
     * Test for duplicate team entry
     */
    @Test
    public void validateTeamWithContest() throws Exception {
        Team team = new Team();
        team.setName("Sharks" + team);
        Contest contest = new Contest();
        // adding same team twice
        contest.getTeams().add(team);
        contest.getTeams().add(team);
    }
}
