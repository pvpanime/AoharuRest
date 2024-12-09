package dev.nemi.aoharu.dto.board;

import dev.nemi.aoharu.dto.PageRequestDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentPageRequestDTO implements PageRequestDTO {

  private static final int DEFAULT_SIZE = 50;

  @Min(1)
  @Positive
  @Builder.Default
  private int page = 1;

  @Min(1)
  @Max(100)
  @Positive
  @Builder.Default
  private int size = DEFAULT_SIZE;
}
