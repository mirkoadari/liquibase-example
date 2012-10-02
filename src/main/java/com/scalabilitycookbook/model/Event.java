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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long id;

  @Column(insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  public Date createdAt;

  @Override
  public String toString() {
    return "Event{" +
      "id=" + id +
      ", createdAt=" + createdAt +
      '}';
  }
}
