package dev.nemi.aoharu.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentEditDTO {
  // comment ID would be specified in path variable.
  private String content;
}
