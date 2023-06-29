package com.github.adrian83.mordeczki.auth.config.database;

import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfig.class);

	@Autowired
	private DatabaseProperties properties;

	@Bean
	public DataSource getDataSource() {
		LOGGER.info("Creating data source with properties: {}", properties);
		var dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName(properties.getDriver());
		dataSourceBuilder.username(properties.getUsername());
		dataSourceBuilder.password(properties.getPassword());
		dataSourceBuilder.url(properties.getUrl());
		return dataSourceBuilder.build();
	}
}
