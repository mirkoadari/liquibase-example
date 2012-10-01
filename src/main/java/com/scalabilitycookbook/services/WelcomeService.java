package com.scalabilitycookbook.services;

import com.scalabilitycookbook.model.Event;
import com.scalabilitycookbook.model.JPA;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Date;

@Path("/")
public class WelcomeService {

  @GET
  @Produces("text/plain")
  public String doGET() {
    JPA.before();

    new Event().save();
    long counter = Event.count();
    Date lastUpdate = Event.lastUpdate();

    JPA.after();
    return String.format("Events %d; last update on %s", counter, lastUpdate);
  }

}
