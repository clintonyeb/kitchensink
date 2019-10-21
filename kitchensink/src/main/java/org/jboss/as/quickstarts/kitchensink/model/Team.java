package org.jboss.as.quickstarts.kitchensink.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinTable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Team entity class
 */

@Entity
@Data
public class Team {

    enum State {
        Accepted, Pending, Canceled;
    }

    @Id
    @GeneratedValue
    private Long id;


    @Column(nullable = false)
    private String name;


    @Column
    private int rank;

    @Column
    @Enumerated(EnumType.STRING)
    private State state;

    @ManyToOne
    @JoinColumn(name = "fk_Contest")
    private Contest contest;


    @ManyToMany
    @JoinTable(name = "person_team",
            joinColumns = { @JoinColumn(name = "Team_ID", referencedColumnName = "ID") },
            inverseJoinColumns = { @JoinColumn(name = "Person_ID", referencedColumnName = "ID") })
    private Set<Person> membership = new HashSet<>();



    @ManyToOne
    @JoinColumn(name = "fk__Person")
    private Person coach;


    @OneToOne
    @JoinColumn(name = "fk_team")
    private Team subTeam;
}
