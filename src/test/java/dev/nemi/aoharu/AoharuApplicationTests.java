package dev.nemi.aoharu;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Log4j2
@SpringBootTest
class AoharuApplicationTests {

  @Autowired
  private DataSource dataSource;

  @Test
  void contextLoads() {
  }

  @Test
  public void hikariTest() throws SQLException {
    @Cleanup Connection connection = dataSource.getConnection();
    @Cleanup PreparedStatement statement = connection.prepareStatement("SHOW TABLES");
    @Cleanup ResultSet rs = statement.executeQuery();
    while (rs.next()) {
      log.info(rs.getString(1));
    }
  }



}
