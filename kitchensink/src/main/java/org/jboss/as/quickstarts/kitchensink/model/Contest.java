package org.jboss.as.quickstarts.kitchensink.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Contest entity class
 */

@Entity
@Data
public class Contest {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private int capacity;

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column
    private Boolean registrationAllowed;

    @Column
    @Temporal(TemporalType.DATE)
    private Date registrationFrom;

    @Column
    @Temporal(TemporalType.DATE)
    private Date registrationTo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contest")
    private Set<Team> teams = new HashSet();

    @ManyToMany
    @JoinTable(name = "person_contest",
            joinColumns = {@JoinColumn(name = "Contest_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "Person_ID", referencedColumnName = "ID")})
    private Set<Person> managers = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "fk_team")
    private Contest subContest;
}
