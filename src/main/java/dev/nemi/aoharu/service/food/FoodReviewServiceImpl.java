package dev.nemi.aoharu.service.food;

import dev.nemi.aoharu.dto.*;
import dev.nemi.aoharu.dto.food.FoodReviewDTO;
import dev.nemi.aoharu.dto.food.FoodReviewEditDTO;
import dev.nemi.aoharu.dto.food.FoodReviewRegisterDTO;
import dev.nemi.aoharu.prime.Food;
import dev.nemi.aoharu.prime.FoodReview;
import dev.nemi.aoharu.repository.food.FoodRepo;
import dev.nemi.aoharu.repository.food.FoodReviewRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodReviewServiceImpl implements FoodReviewService {

  private final FoodReviewRepo foodReviewRepo;
  private final ModelMapper modelMapper;
  private final FoodRepo foodRepo;

  @Override
  public PageResponseDTO<FoodReviewDTO> getReviews(PageRequestDTO requestDTO, Long foodId) {
    Pageable pageable = PageRequest.of(requestDTO.getPage() <= 0 ? 0 : requestDTO.getPage() - 1, requestDTO.getSize());
    Page<FoodReview> reviewPage = foodReviewRepo.getReviewsOf(pageable, foodId);
    List<FoodReviewDTO> reviewDTOList = reviewPage.stream().map(review -> {
      FoodReviewDTO foodReviewDTO = modelMapper.map(review, FoodReviewDTO.class);
      foodReviewDTO.setFoodId(foodId);
      return foodReviewDTO;
    }).toList();
    return PageResponseDTO.<FoodReviewDTO>withAll()
      .pageRequestDTO(requestDTO)
      .total(reviewPage.getTotalElements())
      .dtoList(reviewDTOList)
      .build();
  }

  @Override
  @Transactional
  public FoodReviewDTO getOne(Long reviewId) {
    FoodReview foodReview = foodReviewRepo.findById(reviewId).orElseThrow();
    FoodReviewDTO foodReviewDTO = modelMapper.map(foodReview, FoodReviewDTO.class);

    foodReviewDTO.setFoodId(foodReview.getFood().getId());
    return foodReviewDTO;
  }

  @Override
  public Long addReview(FoodReviewRegisterDTO dto) {
    FoodReview foodReview = modelMapper.map(dto, FoodReview.class);
    Food theFood = foodRepo.findById(dto.getFoodId()).orElseThrow();
    foodReview.setFood(theFood);
    FoodReview result = foodReviewRepo.save(foodReview);
    return result.getId();
  }

  @Override
  public void updateReview(FoodReviewEditDTO dto) {
    FoodReview foodReview = foodReviewRepo.findById(dto.getId()).orElseThrow();
    foodReview.update(dto.getComment(), dto.getRating());
    foodReviewRepo.save(foodReview);
  }

  @Override
  public void deleteReview(long id) {
    foodReviewRepo.deleteById(id);
  }
}
