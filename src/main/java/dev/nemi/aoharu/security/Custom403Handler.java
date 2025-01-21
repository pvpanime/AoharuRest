package dev.nemi.aoharu.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
public class Custom403Handler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    log.info("Access denied");
//    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//    response.setContentType("application/json");
//    response.setCharacterEncoding("UTF-8");
//    PrintWriter out = response.getWriter();
//    out.write("""
//{
//  "success": false,
//  "message": "Access denied"
//}
//      """);
  }
}
