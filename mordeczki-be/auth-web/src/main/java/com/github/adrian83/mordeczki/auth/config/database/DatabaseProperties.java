package com.github.adrian83.mordeczki.auth.config.database;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "datasource")
public class DatabaseProperties {

	private String url;
	private String username;
	private String password;
	private String driver;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	@Override
	public String toString() {
		var pass = password.substring(0, 1) + "***" + password.substring(password.length() - 2, password.length() - 1);
		return "DatabaseProperties [url=%s, username=%s, password=%s, driver=%s]".formatted(url, username, pass,
				driver);
	}
}
