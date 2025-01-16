package dev.nemi.aoharu.controller.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Log4j2
@RestControllerAdvice
public class Arona {

  @ExceptionHandler(value = { AuthenticationException.class })
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<Map<String, Object>> authenticationFailed(
    AuthenticationException ae
  ) {
    log.info(ae);
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
      Map.of(
        "success", false,
        "message", "Access Denied"
      )
    );
  }

  @ExceptionHandler(value = { NoHandlerFoundException.class, NoSuchElementException.class })
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<Map<String, Object>> notFound() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
      Map.of(
        "success", false,
        "message", "Not Found"
      )
    );
  }

  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Map<String, String>> handleBindException(BindException e) {
    log.error(e);
    Map<String, String> errorMap = new HashMap<>();

    if (e.hasErrors()) {
      BindingResult br = e.getBindingResult();
      br.getFieldErrors().forEach(fe -> errorMap.put(fe.getField(), fe.getCode()));
    }
    return ResponseEntity.badRequest().body(errorMap);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
  public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
    log.error(e);
    return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
  }


  @ExceptionHandler(Exception.class)
  public String anyException(Exception ex) {
    Arrays.stream(ex.getStackTrace()).forEach(log::error);
    return ex.getMessage();
  }
}

