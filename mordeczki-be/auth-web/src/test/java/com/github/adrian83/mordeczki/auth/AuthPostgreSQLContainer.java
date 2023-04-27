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

}
