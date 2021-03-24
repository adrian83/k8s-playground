package com.github.adrian83.mordeczki;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
    properties = {
      "DB_URL=jdbc:h2:mem:db;DB_CLOSE_DELAY=-1",
      "DB_USERNAME=localhost",
      "DB_PASSWORD=secret",
      "DB_DRIVER=org.h2.Driver"
    })
class AuthApplicationTests {


  @Test
  void contextLoads() {}
}
