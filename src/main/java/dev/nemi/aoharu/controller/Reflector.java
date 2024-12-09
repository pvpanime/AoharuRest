package dev.nemi.aoharu.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Log4j2
@RestController
public class Reflector {

  @GetMapping("/json")
  public Object json(@RequestParam Map<String, String> allParams) {
    return allParams;
  }
}
