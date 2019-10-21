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
package org.jboss.as.quickstarts.kitchensink.controller;

import org.jboss.as.quickstarts.kitchensink.model.Person;
import org.jboss.as.quickstarts.kitchensink.service.PersonService;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://www.cdi-spec.org/faq/#accordion6
@Model
public class PersonController {

  @Inject
  private FacesContext facesContext;

  @Inject
  private PersonService personService;

  @Produces
  @Named
  private Person newPerson;

  @Produces
  @Named
  private List<String> universities = Arrays.asList
    (
      "Baylor University",
      "Texas State University",
      "Michigan University",
      "Harvard University",
      "Oxford University",
      "Yale University",
      "MIT University"
    );

  public void register() {
    try {
      personService.register(newPerson);
      FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
      facesContext.addMessage(null, m);
      initNewPerson();
    } catch (Exception e) {
      String errorMessage = getRootErrorMessage(e);
      FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
      facesContext.addMessage(null, m);
    }
  }

  @PostConstruct
  public void initNewPerson() {
    newPerson = new Person();
  }

  private String getRootErrorMessage(Exception e) {
    // Default to general error message that registration failed.
    String errorMessage = "Registration failed. See server log for more information";
    if (e == null) {
      // This shouldn't happen, but return the default messages
      return errorMessage;
    }

    // Start with the exception and recurse to find the root cause
    Throwable t = e;
    while (t != null) {
      // Get the message from the Throwable class instance
      errorMessage = t.getLocalizedMessage();
      t = t.getCause();
    }
    // This is the root cause message
    return errorMessage;
  }

  public String update() {
    try {
      this.newPerson = personService.updatePerson(newPerson);
      FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Updated!", "Updated successful");
      facesContext.addMessage(null, m);
      initNewPerson();
      return "index.xhtml";
    } catch (Exception e) {
      String errorMessage = getRootErrorMessage(e);
      FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Update unsuccessful");
      facesContext.addMessage(null, m);
      return "update.xhtml";
    }
  }

  //    public String showAll() {
  //        this.people = personService.findAll();
  //        return "show-all.xhtml";
  //    }

  public String show(Long personId) {
    this.newPerson = personService.findPerson(personId);
    return "show.xhtml";
  }

  public String getUpdate(Long personId) {
    this.newPerson = personService.findPerson(personId);
    System.out.println(this.newPerson.getId() + " before update");
    return "update.xhtml";
  }

  public void delete(Long personId) {
    try {
      personService.deletePerson(personId);
      FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Deleted!", "Person deletion successful");
      facesContext.addMessage(null, m);
      //            initNewPerson();
    } catch (Exception e) {
      String errorMessage = getRootErrorMessage(e);
      FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Deletion unsuccessful");
      facesContext.addMessage(null, m);
    }
  }

}
