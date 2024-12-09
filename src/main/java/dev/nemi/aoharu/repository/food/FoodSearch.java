package dev.nemi.aoharu.repository.food;

import dev.nemi.aoharu.dto.food.FoodViewCompactDTO;
import dev.nemi.aoharu.dto.food.FoodViewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface FoodSearch {

  Page<FoodViewCompactDTO> getFoods(Pageable pageable, String searchName, Long minPrice, Long maxPrice, Integer minRate, LocalDateTime until);

  Page<FoodViewDTO> getFoodsImageSupport(Pageable pgb, String searchName, Long minPrice, Long maxPrice, Integer minRate, LocalDateTime until);

  default Page<FoodViewDTO> getFoodsImageSupport(Pageable pgb) {
    return getFoodsImageSupport(pgb, null, null, null, null, null);
  }
}
