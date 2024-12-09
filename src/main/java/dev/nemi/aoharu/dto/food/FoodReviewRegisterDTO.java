package dev.nemi.aoharu.dto.food;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FoodReviewRegisterDTO {
  @NotNull
  private Long foodId;

  @NotBlank
  private String comment;

  @NotNull
  @Min(1)
  @Max(5)
  private Integer rating;

  private String userid;
}
