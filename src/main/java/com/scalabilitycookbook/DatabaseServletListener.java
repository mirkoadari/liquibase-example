package com.scalabilitycookbook;

import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.ServerConfig;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.CompositeResourceAccessor;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.h2.jdbcx.JdbcDataSource;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;

public class DatabaseServletListener implements ServletContextListener {

  public DatabaseServletListener() {}

  public void contextInitialized(ServletContextEvent sce) {
    String changeLog = sce.getServletContext().getInitParameter("liquibase.changelog");

    if (changeLog == null) {
      throw new RuntimeException("Cannot run Liquibase, liquibase.changelog is not set");
    }

    try {
      JdbcDataSource dataSource = new JdbcDataSource();
      dataSource.setURL("jdbc:h2:target/database");

      ServerConfig sc = new ServerConfig();
      sc.setName("default");
      sc.setDefaultServer(true);
      sc.loadFromProperties();
      sc.setDataSource(dataSource);
      EbeanServerFactory.create(sc);

      Connection connection = null;
      try {
        connection = dataSource.getConnection();

        Thread currentThread = Thread.currentThread();
        ClassLoader contextClassLoader = currentThread.getContextClassLoader();
        ResourceAccessor threadClFO = new ClassLoaderResourceAccessor(contextClassLoader);

        ResourceAccessor clFO = new ClassLoaderResourceAccessor();
        ResourceAccessor fsFO = new FileSystemResourceAccessor();

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new Liquibase(changeLog, new CompositeResourceAccessor(clFO, fsFO, threadClFO), database);

        liquibase.update(null);
      }
      finally {
        if (connection != null)
          connection.close();
      }
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void contextDestroyed(ServletContextEvent sce) {
  }

}
