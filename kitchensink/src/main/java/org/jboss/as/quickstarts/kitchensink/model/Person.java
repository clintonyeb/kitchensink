package org.jboss.as.quickstarts.kitchensink.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Person entity class
 */
@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@XmlRootElement
public class Person implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  @Column
  @NotNull
  @NotBlank
  @Pattern(regexp = "[A-Za-z ]*", message = "must contain only letters and spaces")
  private String name;

  @NotNull
  @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "must contain valid email address")
  @Column
  private String email;

  @Column
  @NotNull
  private String university;

  //Using LocalDate since only birthDate part is needed.
  @Column
  @Temporal(TemporalType.DATE)
  @NotNull
  private Date birthDate;

  //  @OneToMany(fetch = FetchType.LAZY, mappedBy = "coach")
  //  private Set<Team> teams = new HashSet();

  //  @ManyToMany//(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  //  @JoinTable(name = "person_team",
  //    joinColumns = {@JoinColumn(name = "Person_ID", referencedColumnName = "ID")},
  //    inverseJoinColumns = {@JoinColumn(name = "Team_ID", referencedColumnName = "ID")})
  //  private Set<Team> membership = new HashSet<>();

  //  @ManyToMany
  //  @JoinTable(name = "person_contest",
  //    joinColumns = {@JoinColumn(name = "Person_ID", referencedColumnName = "ID")},
  //    inverseJoinColumns = {@JoinColumn(name = "Contest_ID", referencedColumnName = "ID")})
  //  private Set<Contest> contests = new HashSet<>();
}
