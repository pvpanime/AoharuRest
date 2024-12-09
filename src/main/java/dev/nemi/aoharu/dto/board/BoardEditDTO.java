package dev.nemi.aoharu.dto.board;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardEditDTO {
  private Long bid;

  @NotBlank
  private String title;

  @NotBlank
  private String content;
}
