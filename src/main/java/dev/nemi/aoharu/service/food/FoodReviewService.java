package dev.nemi.aoharu.service.food;


import dev.nemi.aoharu.dto.*;
import dev.nemi.aoharu.dto.food.FoodReviewDTO;
import dev.nemi.aoharu.dto.food.FoodReviewEditDTO;
import dev.nemi.aoharu.dto.food.FoodReviewRegisterDTO;

public interface FoodReviewService {
  PageResponseDTO<FoodReviewDTO> getReviews(PageRequestDTO requestDTO, Long foodId);

  FoodReviewDTO getOne(Long reviewId);

  Long addReview(FoodReviewRegisterDTO dto);

  void updateReview(FoodReviewEditDTO dto);

  void deleteReview(long id);
}
