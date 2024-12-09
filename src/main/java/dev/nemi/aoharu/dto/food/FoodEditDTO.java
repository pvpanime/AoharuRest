package dev.nemi.aoharu.dto.food;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodEditDTO {
  private Long id;
  private String name;
  private String description;
  private Long price;
  private Long stock;
  private LocalDateTime opened;
  private LocalDateTime close;
  private List<String> imageFiles;
}
