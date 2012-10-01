package com.scalabilitycookbook.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Event {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long id;

  public void save() {
   JPA.em().persist(this);
  }

  public static Long count() {
    return JPA.em().createQuery("select count(*) from Event", Long.class).getSingleResult();
  }

}
