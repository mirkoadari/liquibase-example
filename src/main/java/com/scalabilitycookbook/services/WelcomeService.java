package com.scalabilitycookbook.services;

import com.scalabilitycookbook.model.Event;
import com.scalabilitycookbook.model.JPA;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class WelcomeService {

  @GET
  @Produces("text/plain")
  public String doGET() {
    JPA.before();

    new Event().save();
    long counter = Event.count();

    JPA.after();
    return String.format("Events %d", counter);
  }

}
