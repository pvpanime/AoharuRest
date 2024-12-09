package dev.nemi.aoharu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThymeDTO {
  private int scalar;
  private String message;
  private LocalDateTime chronal;
}
