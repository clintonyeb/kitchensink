/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.kitchensink.service;

import org.jboss.as.quickstarts.kitchensink.model.Person;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class PersonService {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Person> personEventSrc;

    public Person register(Person person) {
        log.info("Registering person " + person.getName());
        Person p = em.merge(person);
        personEventSrc.fire(person);
        return p;
    }

    public Person updatePerson(Person person) {
        log.info("Updating person " + person.getName());

        // check id is set on person
        if(person.getId() == null) {
            return register(person);
        } else {
            Person oldPerson = findPerson(person.getId());

            if(oldPerson == null) {
                return register(person);
            }

            em.detach(oldPerson);
            person = em.merge(person);

            personEventSrc.fire(person);

            // log update
            logPersonUpdate(oldPerson, person);

            return person;
        }
    }

    private void logPersonUpdate(Person old, Person person) {
        System.out.println("\n\n");
        log.info("---------Person update complete---------");
        log.info(String.format("Person with Id: %d", person.getId()));
        log.info(String.format("Name: %s -> %s", old.getName(), person.getName()));
        log.info(String.format("Email: %s -> %s", old.getEmail(), person.getEmail()));
        log.info(String.format("University: %s -> %s", old.getUniversity(), person.getUniversity()));
        log.info(String.format("BirthDate: %s -> %s", old.getBirthDate(), formatDate(person.getBirthDate())));
        log.info("---------Person update complete---------");
        System.out.println("\n\n");
    }

    public Person findPerson(Long personId) {
        return em.find(Person.class, personId);
    }

    public List<Person> findAll() {
        Query query = em.createQuery("SELECT * FROM Person");
        return (List<Person>) query.getResultList();
    }

    public void deletePerson(Long personId) {
        Person person = em.find(Person.class, personId);
        log.info("Deleting person " + person.getName());
        em.remove(person);
        personEventSrc.fire(person);
    }

    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
}
