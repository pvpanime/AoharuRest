package dev.nemi.aoharu.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignupDTO {

  @Pattern(regexp = "^[\\w-_]+$")
  private String userid;
  private String password;
  private String email;

  @Builder.Default
  private boolean deleted = false;

  @Builder.Default
  private Integer social = null;
}
