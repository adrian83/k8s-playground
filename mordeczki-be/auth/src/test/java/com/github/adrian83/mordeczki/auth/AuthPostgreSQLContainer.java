package com.github.adrian83.mordeczki.auth;

import org.testcontainers.containers.PostgreSQLContainer;

public class AuthPostgreSQLContainer extends PostgreSQLContainer<AuthPostgreSQLContainer> {

  private static final String IMAGE_VERSION = "postgres:11.1";
  private static final String INIT_SCRIPT_PATH = "db-schema.sql"; // src/test/resources

  private static AuthPostgreSQLContainer container;

  private AuthPostgreSQLContainer() {
    super(IMAGE_VERSION);
  }

  public static AuthPostgreSQLContainer getInstance() {
    if (container == null) {
      container = create().withInitScript(INIT_SCRIPT_PATH);
    }
    return container;
  }

  private static AuthPostgreSQLContainer create() {
    return new AuthPostgreSQLContainer();
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("DB_URL", container.getJdbcUrl());
    System.setProperty("DB_USERNAME", container.getUsername());
    System.setProperty("DB_PASSWORD", container.getPassword());
    System.setProperty("DB_DRIVER", "org.postgresql.Driver");
  }

  @Override
  public void stop() {
    //container.close();
  }
}
