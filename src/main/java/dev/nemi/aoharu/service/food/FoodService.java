package dev.nemi.aoharu.service.food;

import dev.nemi.aoharu.dto.*;
import dev.nemi.aoharu.dto.food.FoodEditDTO;
import dev.nemi.aoharu.dto.food.FoodPageRequestDTO;
import dev.nemi.aoharu.dto.food.FoodRegisterDTO;
import dev.nemi.aoharu.dto.food.FoodViewDTO;
import dev.nemi.aoharu.prime.Food;

public interface FoodService {

  PageResponseDTO<FoodViewDTO> getFoods(FoodPageRequestDTO requestDTO);

  FoodViewDTO getOne(long id);

  Long register(FoodRegisterDTO dto);

  void edit(FoodEditDTO dto);

  void delete(long id);

  default Food entityOf(FoodRegisterDTO dto) {
    Food food = Food.builder()
      .name(dto.getName())
      .description(dto.getDescription())
      .price(dto.getPrice())
      .stock(dto.getStock())
      .opened(dto.getOpened())
      .close(dto.getClose())
      .registrar(dto.getRegistrar())
      .build();

    dto.getImageFileNames().forEach(filename -> {
      String[] body = filename.split("_");
      food.addImage(body[0], body[1]);
    });

    return food;
  }



}
