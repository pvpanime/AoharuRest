package dev.nemi.aoharu;


import dev.nemi.aoharu.repository.food.FoodRepo;
import dev.nemi.aoharu.dto.food.FoodRegisterDTO;
import dev.nemi.aoharu.service.food.FoodService;
import dev.nemi.aoharu.dto.food.FoodViewDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Log4j2
@SpringBootTest
public class FoodTest {

  @Autowired
  private FoodRepo foodRepo;

  @Autowired
  private FoodService foodService;

//  @Test
//  public void addImageTest() {
//    Food food = foodRepo.getOneWithImages(1L).orElseThrow();
//    food.addImage("d9575130-9e5b-4c31-a6e7-934aa0c92e53", "bob1.png");
//    food.addImage("0edb15e7-ac1e-4cd9-8d78-90f576daff52", "bob2.png");
//    food.addImage("10f6ebc3-6520-416c-a4b6-008f57f366c6", "bob3.png");
//    foodRepo.save(food);
//  }

  @Test
  public void getFoodsWithImageTest() {
    FoodViewDTO foodDTO = foodService.getOne(1L);
    log.info("foodDTO: {}", foodDTO);

    FoodViewDTO foodDTO2 = foodService.getOne(11L);
    log.info("foodDTO2: {}", foodDTO2);
  }

  @Transactional
  @Test
  public void n1Test() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
    Page<FoodViewDTO> dtoPage = foodRepo.getFoodsImageSupport(pageable);
    for (FoodViewDTO dto : dtoPage) {
      log.info("dto: {}", dto);
    }
  }

  @Test
  public void foodWithImageRegisterTest() {

    FoodRegisterDTO foodRegisterDTO = FoodRegisterDTO.builder()
      .name("요아정")
      .description("요아정이 뭐임?")
      .price(10000L)
      .stock(300L)
      .opened(LocalDateTime.now())
      .close(LocalDateTime.now().plusYears(1L))
      .registrar("hina")
      .imageFileNames(List.of(
        UUID.randomUUID() + "_" + "yoajeong1.png",
        UUID.randomUUID() + "_" + "yoajeong2.png",
        UUID.randomUUID() + "_" + "yoajeong3.png"
        ))
      .build();

    Long id = foodService.register(foodRegisterDTO);
    log.info("id: {}", id);
  }

  @Test
  public void foodViewTest() {
    FoodViewDTO dto = foodService.getOne(8193L);
    log.info("dto: {}", dto);
  }

  @Test
  @Transactional
  public void foodDeleteTest() {
    foodService.delete(4998L);
    foodService.delete(5000L);
  }

}
