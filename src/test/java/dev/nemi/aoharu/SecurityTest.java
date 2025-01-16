package dev.nemi.aoharu;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class SecurityTest {

  @Autowired
  private JwtUtils jwtUtils;

  @Test
  public void jwtTest() {
    String token = jwtUtils.generateJwt("hina");
    log.info(token);
    boolean validated = jwtUtils.validateJwt(token);
    log.info("validated: {}", validated);
    String username = jwtUtils.getUsernameFromJwt(token);
    log.info("username: {}", username);
  }
}
