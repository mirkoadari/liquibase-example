package com.scalabilitycookbook.services;

import com.avaje.ebean.Ebean;
import com.scalabilitycookbook.model.Event;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class WelcomeService {

  @GET
  @Produces("text/plain")
  public String welcome() {
    Event e = new Event();
    Ebean.save(e);
    Ebean.refresh(e); // refresh to get the generated createdAt
    return e.toString();
  }

}
