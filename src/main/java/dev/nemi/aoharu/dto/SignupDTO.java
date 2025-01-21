package dev.nemi.aoharu.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignupDTO {

  @Pattern(regexp = "^[\\w-_]+$")
  private String username;
  private String password;
  private String email;

  @Builder.Default
  private boolean deleted = false;

  @Builder.Default
  private Integer social = null;
}
