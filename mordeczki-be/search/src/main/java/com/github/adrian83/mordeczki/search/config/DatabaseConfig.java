package com.github.adrian83.mordeczki.search.config;

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

  @Autowired private Environment env;

  @Bean
  public DataSource getDataSource() {

    var dbHost = env.getRequiredProperty("DB_URL");
    var dbUsername = env.getRequiredProperty("DB_USERNAME");
    var dbPassword = env.getRequiredProperty("DB_PASSWORD");
    var dbUrl = String.format("jdbc:postgresql://%s:5432/search", dbHost);
    
    log.warn("Connecting do DB. Url: {}, user: {}", dbUrl, dbUsername);

    var dataSourceBuilder = DataSourceBuilder.create();
    dataSourceBuilder.driverClassName("org.postgresql.Driver");
    dataSourceBuilder.url(dbUrl);
    dataSourceBuilder.username(dbUsername);
    dataSourceBuilder.password(dbPassword);
    return dataSourceBuilder.build();
  }
}
