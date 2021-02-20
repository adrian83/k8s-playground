package com.github.adrian83.mordeczki.auth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DatabaseConfig {

  public static final String PROP_DB_USERNAME = "DB_USERNAME";
  public static final String PROP_DB_PASSWORD = "DB_PASSWORD";
  public static final String PROP_DB_DRIVER = "DB_DRIVER";
  public static final String PROP_DB_URL = "DB_URL";

  @Autowired private Environment env;

  @Bean
  public DataSource getDataSource() {
    var dbUrl = env.getRequiredProperty(PROP_DB_URL);
    var dbUsername = env.getRequiredProperty(PROP_DB_USERNAME);
    var dbPassword = env.getRequiredProperty(PROP_DB_PASSWORD);
    var dbDriver = env.getRequiredProperty(PROP_DB_DRIVER);

    log.warn("Connecting do DB. Url: {}, user: {}", dbUrl, dbUsername);

    var dataSourceBuilder = DataSourceBuilder.create();
    dataSourceBuilder.driverClassName(dbDriver);
    dataSourceBuilder.username(dbUsername);
    dataSourceBuilder.password(dbPassword);
    dataSourceBuilder.url(dbUrl);
    return dataSourceBuilder.build();
  }
}
