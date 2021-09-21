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
    var dbUrl = env.getProperty(PROP_DB_URL);
    var dbUsername = env.getProperty(PROP_DB_USERNAME);
    var dbPassword = env.getProperty(PROP_DB_PASSWORD);
    var dbDriver = env.getProperty(PROP_DB_DRIVER);
    
    if(dbUrl == null && dbUsername == null && dbPassword == null && dbDriver == null) {
    	dbUrl = System.getProperty(PROP_DB_URL);
    	dbUsername= System.getProperty(PROP_DB_USERNAME);
    	dbPassword= System.getProperty(PROP_DB_PASSWORD);
    	dbDriver= System.getProperty(PROP_DB_DRIVER);
    }

    log.info("Connecting do DB. Url: {}, user: {}", dbUrl, dbUsername);

    var dataSourceBuilder = DataSourceBuilder.create();
    dataSourceBuilder.driverClassName(dbDriver);
    dataSourceBuilder.username(dbUsername);
    dataSourceBuilder.password(dbPassword);
    dataSourceBuilder.url(dbUrl);
    return dataSourceBuilder.build();
  }
}
