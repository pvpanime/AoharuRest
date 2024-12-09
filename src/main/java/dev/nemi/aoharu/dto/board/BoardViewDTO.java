package dev.nemi.aoharu.dto.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardViewDTO {
  private Long bid;
  private String title;
  private String content;
  private String userid;
  private Integer status;
  private LocalDateTime added;
  private LocalDateTime updated;
}
