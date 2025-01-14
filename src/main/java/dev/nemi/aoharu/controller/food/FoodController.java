package dev.nemi.aoharu.controller.food;

import dev.nemi.aoharu.dto.*;
import dev.nemi.aoharu.dto.food.*;
import dev.nemi.aoharu.service.food.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {

  private final FoodService foodService;

  @GetMapping("/list")
  public
  ResponseEntity<RestResponseDTO<PageResponseDTO<FoodViewDTO>>>
  getFoods(
    @Valid FoodPageRequestDTO requestDTO,
    BindingResult requestBR
  ) throws BindException {
    if (requestBR.hasErrors()) throw new BindException(requestBR);
    PageResponseDTO<FoodViewDTO> responseDTO = foodService.getFoods(requestDTO);
    return RestResponseDTO.ok(responseDTO);
  }

  @GetMapping("/view/{id}")
  public
  ResponseEntity<RestResponseDTO<FoodViewDTO>>
  getOne(@PathVariable long id) {
    FoodViewDTO food = foodService.getOne(id);
    return RestResponseDTO.ok(food);
  }

  @PostMapping("/register")
  public ResponseEntity<RestResponseDTO<FoodRegisterResponseDTO>> register(
    @Valid FoodRegisterDTO registerDTO,
    BindingResult registerBR
  ) throws BindException {
    if (registerBR.hasErrors()) throw new BindException(registerBR);

    Long id = foodService.register(registerDTO);
    return RestResponseDTO.ok(FoodRegisterResponseDTO.builder().foodId(id).build());
  }


  @PostMapping("/edit")
  public ResponseEntity<RestResponseDTO.Void> edit(
    @Valid FoodEditDTO editDTO,
    BindingResult editBR
  ) throws BindException {
    if (editBR.hasErrors()) throw new BindException(editBR);
    foodService.edit(editDTO);
    return RestResponseDTO.Void.ok("");
  }

  @Tag(name = "Delete a food")
  @DeleteMapping(value = "/delete/{foodId}")
  public ResponseEntity<Map<String, Object>> deleteFood(
    @PathVariable long foodId
  ) {
    foodService.delete(foodId);
    Map<String, Object> responseBody = Map.of("success", true);
    return ResponseEntity.ok(responseBody);
  }


}
