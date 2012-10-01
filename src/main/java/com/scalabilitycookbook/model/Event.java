package com.scalabilitycookbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class Event {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public Long id;

  @Column(insertable = false)
  @Temporal(TemporalType.TIMESTAMP)
  public Date createdAt;

  public void save() {
   JPA.em().persist(this);
  }

  public static Long count() {
    return JPA.em().createQuery("select count(*) from Event", Long.class).getSingleResult();
  }

  public static Date lastUpdate() {
    return JPA.em().createQuery("select max(createdAt) from Event", Date.class).getSingleResult();
  }

}
