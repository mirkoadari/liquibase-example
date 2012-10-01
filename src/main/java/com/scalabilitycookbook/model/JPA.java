package com.scalabilitycookbook.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPA {
  private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("liquibase-example");
  private static final ThreadLocal<EntityManager> persistence = new ThreadLocal<EntityManager>();

  public static void before() {
    EntityManager em = factory.createEntityManager();
    em.getTransaction().begin();
    persistence.set(em);
  }

  public static void after() {
    EntityManager em = persistence.get();
    em.getTransaction().commit();
    em.close();
    persistence.set(null);
  }

  public static EntityManager em() {
    return persistence.get();
  }
}
