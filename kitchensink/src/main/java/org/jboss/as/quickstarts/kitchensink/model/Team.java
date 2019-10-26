package org.jboss.as.quickstarts.kitchensink.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Team entity class
 */

@Entity
@Data
public class Team implements Serializable {

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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "person_team",
            joinColumns = {@JoinColumn(name = "Team_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "Person_ID", referencedColumnName = "ID")})
    private Set<Person> membership = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "fk__Person")
    private Person coach;
    @OneToOne
    @JoinColumn(name = "fk_team")
    private Team subTeam;


    public enum State {
        Accepted, Pending, Canceled;
    }
}
