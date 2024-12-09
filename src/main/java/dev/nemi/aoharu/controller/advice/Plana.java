package dev.nemi.aoharu.controller.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Log4j2
@RestControllerAdvice(annotations = RestController.class)
public class Plana {

  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
  public ResponseEntity<Map<String, String>> handleBindException(BindException e) {
    log.error("It's bind exception?!");
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

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
  public ResponseEntity<Map<String, String>> handleNoSuchElementException(NoSuchElementException e) {
    log.error(e);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
  }
}

