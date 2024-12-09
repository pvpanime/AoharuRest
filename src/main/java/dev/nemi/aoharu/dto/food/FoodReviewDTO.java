package dev.nemi.aoharu.dto.food;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FoodReviewDTO {
  private Long foodId;
  private Long id;
  private String comment;
  private Integer rating;
  private String userid;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime added;
}
