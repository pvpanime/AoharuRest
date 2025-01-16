package dev.nemi.aoharu.controller;

import dev.nemi.aoharu.JwtUtils;
import dev.nemi.aoharu.dto.JwtResponseDTO;
import dev.nemi.aoharu.dto.LoginRequestDTO;
import dev.nemi.aoharu.dto.SignupDTO;
import dev.nemi.aoharu.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AuthController {

  private final JwtUtils jwtUtils;

  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<Map<String, Object>> doSignup(
    SignupDTO signupDTO
  ) {
    userService.insertUser(signupDTO);
    return ResponseEntity.ok().body(
      Map.of("success", Boolean.TRUE, "message", "Welcome, " + signupDTO.getUserid() +"!")
    );
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequestDTO requestDTO) {
    var token = new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword());
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Generate JWT
    String jwt = jwtUtils.generateJwt(requestDTO.getUsername());
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Authorization", "Bearer " + jwt);
    return ResponseEntity.ok(new JwtResponseDTO(jwt));
  }
}
