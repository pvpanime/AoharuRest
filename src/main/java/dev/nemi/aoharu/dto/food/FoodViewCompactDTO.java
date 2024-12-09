package dev.nemi.aoharu.dto.food;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FoodViewCompactDTO {
  private Long id;
  private String name;
  private String description;
  private Long price;
  private Long stock;
  private LocalDateTime opened;
  private LocalDateTime close;
  private String registrar;
  private LocalDateTime added;
  private LocalDateTime updated;
  private Long reviewCount;
  private Double avgRate;
}
