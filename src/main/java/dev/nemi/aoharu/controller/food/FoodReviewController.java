package dev.nemi.aoharu.controller.food;

import dev.nemi.aoharu.dto.PageResponseDTO;
import dev.nemi.aoharu.dto.food.FoodReviewDTO;
import dev.nemi.aoharu.dto.food.FoodReviewEditDTO;
import dev.nemi.aoharu.dto.food.FoodReviewPageRequestDTO;
import dev.nemi.aoharu.dto.food.FoodReviewRegisterDTO;
import dev.nemi.aoharu.service.food.FoodReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/api/foodReview")
@RequiredArgsConstructor
public class FoodReviewController {

  private final FoodReviewService foodReviewService;

//  @Tag(name = "Get reviews for the food of given id.")
  @GetMapping("/listFor/{foodId}")
  public ResponseEntity<PageResponseDTO<FoodReviewDTO>> getReviewsFor(
    @Valid FoodReviewPageRequestDTO requestDTO,
    BindingResult bindingResult,
    @PathVariable long foodId
  ) throws BindException {
    if (bindingResult.hasErrors()) throw new BindException(bindingResult);
    PageResponseDTO<FoodReviewDTO> responseDTO = foodReviewService.getReviews(requestDTO, foodId);
    return ResponseEntity.ok().body(responseDTO);
  }

//  @Tag(name = "Get a review")
  @GetMapping("/{reviewId}")
  public ResponseEntity<FoodReviewDTO> getReview(
    @PathVariable long reviewId
  ) {
    FoodReviewDTO dto = foodReviewService.getOne(reviewId);
    return ResponseEntity.ok().body(dto);
  }

//  @Tag(name = "Register a review")
  @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Object>> register(
    @Valid @RequestBody FoodReviewRegisterDTO dto,
    BindingResult bindingResult
  ) throws BindException {
    if (bindingResult.hasErrors()) throw new BindException(bindingResult);
    Long id = foodReviewService.addReview(dto);
    Map<String, Object> responseBody = Map.of("success", true, "id", id);
    return ResponseEntity.ok(responseBody);
  }

//  @Tag(name = "Edit a review")
  @PutMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Object>> update(
    @Valid @RequestBody FoodReviewEditDTO dto,
    BindingResult bindingResult
  ) throws BindException {
    if (bindingResult.hasErrors()) throw new BindException(bindingResult);
    foodReviewService.updateReview(dto);
    Map<String, Object> responseBody = Map.of("success", true);
    return ResponseEntity.ok(responseBody);
  }

//  @Tag(name = "Delete a review")
  @DeleteMapping(value = "/{reviewId}")
  public ResponseEntity<Map<String, Object>> deleteReview(
    @PathVariable long reviewId
  ) {
    foodReviewService.deleteReview(reviewId);
    Map<String, Object> responseBody = Map.of("success", true);
    return ResponseEntity.ok(responseBody);
  }

}
