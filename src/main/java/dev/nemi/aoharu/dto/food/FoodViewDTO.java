package dev.nemi.aoharu.dto.food;

import dev.nemi.aoharu.dto.ImageDTO;
import dev.nemi.aoharu.prime.Food;
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
public class FoodViewDTO {
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

  @Builder.Default
  private List<ImageDTO> images = List.of();

  public static FoodViewDTO of(Food food, Long reviewCount, Double avgRate) {
    List<ImageDTO> imageList = food.getImages().stream().sorted().map(
      im -> ImageDTO.builder()
        .id(im.getId()).name(im.getFilename()).ordinal(im.getOrdinal()).build()
    ).toList();

    return FoodViewDTO.builder()
      .id(food.getId())
      .name(food.getName())
      .description(food.getDescription())
      .price(food.getPrice())
      .stock(food.getStock())
      .opened(food.getOpened())
      .close(food.getClose())
      .registrar(food.getRegistrar())
      .added(food.getAdded())
      .updated(food.getUpdated())
      .reviewCount(reviewCount)
      .avgRate(avgRate)
      .images(imageList)
      .build();
  }
}
