package dev.nemi.aoharu.dto.food;

import dev.nemi.aoharu.dto.PageRequestDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodReviewPageRequestDTO implements PageRequestDTO {

  private static final int DEFAULT_PAGE_SIZE = 10;
  public static final String DEFAULT_SIZE_STR = DEFAULT_PAGE_SIZE + "";
  public static final FoodReviewPageRequestDTO DEFAULT = FoodReviewPageRequestDTO.builder().build();

  @Min(1)
  @Positive
  @Builder.Default
  private int page = 1;

  @Min(1)
  @Max(100)
  @Positive
  @Builder.Default
  private int size = DEFAULT_PAGE_SIZE;

}
