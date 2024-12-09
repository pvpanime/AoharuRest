//package dev.nemi.aoharu.controller.advice;
//
//import lombok.extern.log4j.Log4j2;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//import java.util.Arrays;
//import java.util.NoSuchElementException;
//
//@Log4j2
//@ControllerAdvice(annotations = Controller.class)
//public class Arona {
//
//  @ExceptionHandler(value = { NoHandlerFoundException.class, NoSuchElementException.class })
//  @ResponseStatus(HttpStatus.NOT_FOUND)
//  public ResponseEntity<String> notFound() {
//    return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.TEXT_PLAIN).body("Not found");
//  }
//
//  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//  @ResponseStatus(HttpStatus.BAD_REQUEST)
//  public String handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
//    String paramName = ex.getName();
//    String paramValue = ex.getValue() != null ? ex.getValue().toString() : "null";
//    String errorMessage = String.format("Invalid value '%s' for parameter '%s'. Expected a number.", paramValue, paramName);
//    log.error(errorMessage);
//    return "view404";
//  }
//
//  @ResponseBody
//  @ExceptionHandler(Exception.class)
//  public String anyException(Exception ex) {
//    Arrays.stream(ex.getStackTrace()).forEach(log::error);
//    return ex.getMessage();
//  }
//
//
//}
