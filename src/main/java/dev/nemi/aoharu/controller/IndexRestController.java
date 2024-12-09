package dev.nemi.aoharu.controller;

import dev.nemi.aoharu.dto.SignupDTO;
import dev.nemi.aoharu.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
public class IndexRestController {

  private final UserService userService;

  @GetMapping(value = "/json", produces = "application/json")
  public Object json(@RequestParam MultiValueMap<String, String> allParams) {
    log.info("Received query params: {}", allParams);

    Map<String, Object> result = new HashMap<>();
    for (Map.Entry<String, List<String>> entry : allParams.entrySet()) {
      List<String> values = entry.getValue();
      result.put(entry.getKey(), values.size() == 1 ? values.get(0) : values);
    }

    return result;
  }



  @PostMapping("/api/user/signup")
  public ResponseEntity<Map<String, Object>> doSignup(
    SignupDTO signupDTO
  ) {

    userService.insertUser(signupDTO);

    return ResponseEntity.ok().body(
      Map.of("success", Boolean.TRUE, "message", "Welcome, " + signupDTO.getUserid() +"!")
    );
  }
}
