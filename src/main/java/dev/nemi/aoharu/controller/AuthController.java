package dev.nemi.aoharu.controller;

import dev.nemi.aoharu.security.JwtUtils;
import dev.nemi.aoharu.dto.JwtResponseDTO;
import dev.nemi.aoharu.dto.LoginRequestDTO;
import dev.nemi.aoharu.dto.SignupDTO;
import dev.nemi.aoharu.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AuthController {

  private final JwtUtils jwtUtils;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<Map<String, Object>> doSignup(
    @RequestBody SignupDTO signupDTO
  ) {
    log.info("signup: {}", signupDTO);
    userService.insertUser(signupDTO);
    return ResponseEntity.ok().body(
      Map.of("success", Boolean.TRUE, "message", "Welcome, " + signupDTO.getUsername() + "!")
    );
  }

  @PostMapping("/login")
  public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginRequestDTO requestDTO) {
    var token = new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword());
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Generate JWT
    String jwt = jwtUtils.createJwt(authentication);
    log.info(jwtUtils.getAuthentication(jwt));
//    HttpHeaders httpHeaders = new HttpHeaders();
//    httpHeaders.add("Authorization", "Bearer " + jwt);
//    return ResponseEntity.ok().headers(httpHeaders).body(new JwtResponseDTO(jwt));
    return ResponseEntity.ok().body(new JwtResponseDTO(jwt));
  }

  @GetMapping("/identify")
  public ResponseEntity<Map<String, Object>> identify(
    @AuthenticationPrincipal UserDetails user
  ) {
    if (user == null)
      return ResponseEntity.ok().body(Map.of(
        "type", "anonymous"
      ));
    return ResponseEntity.ok().body(Map.of(
      "type", "authenticated",
      "user", user.getUsername()
    ));
  }
}
