package dev.nemi.aoharu.controller;

import dev.nemi.aoharu.ThymeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
@Log4j2
public class IndexController {

  @GetMapping("/")
  public ResponseEntity<String> index() {
    return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Hello!");
  }

  @GetMapping("/ty")
  public void ty(Model model) {
    model.addAttribute("name", "Hello World!");
    model.addAttribute("list", new String[] { "The", "Quick", "Brown", "Fox", "Jumps", "Over" });
    model.addAttribute("dto", ThymeDTO.builder().scalar(42).message("The Universe").chronal(LocalDateTime.now()).build());
  }

}
