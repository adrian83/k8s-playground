package com.github.adrian83.mordeczki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MigrationApplication {

  public static void main(String[] args) {
    // SpringApplication.run(MigrationApplication.class, args);

    ConfigurableApplicationContext ctx =
        new SpringApplicationBuilder(MigrationApplication.class).web(WebApplicationType.NONE).run();

    int exitCode = SpringApplication.exit(ctx, () -> 0);

    System.exit(exitCode);
  }
}
