package dev.nemi.aoharu.repository.food;

import dev.nemi.aoharu.prime.Food;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FoodRepo extends JpaRepository<Food, Long>, FoodSearch {

  @EntityGraph(attributePaths = { "images" })
  @Query("select f from Food f where f.id = :foodId")
  Optional<Food> getOneWithImages(Long foodId);

}
