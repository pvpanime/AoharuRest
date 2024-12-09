package dev.nemi.aoharu.repository.food;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import dev.nemi.aoharu.prime.Food;
import dev.nemi.aoharu.prime.QFood;
import dev.nemi.aoharu.prime.QFoodReview;
import dev.nemi.aoharu.dto.food.FoodViewCompactDTO;
import dev.nemi.aoharu.dto.food.FoodViewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("unused")
public class FoodSearchImpl extends QuerydslRepositorySupport implements FoodSearch {

  public FoodSearchImpl() { super(Food.class); }

  @Override
  public Page<FoodViewCompactDTO> getFoods(Pageable pageable, String searchName, Long minPrice, Long maxPrice, Integer minRate, LocalDateTime until) {

    QFood food = QFood.food;
    QFoodReview foodReview = QFoodReview.foodReview;
    JPQLQuery<Food> foodJPQLQuery = from(food);

    foodJPQLQuery.leftJoin(foodReview).on(foodReview.food.eq(food));
    foodJPQLQuery.groupBy(food);

    BooleanBuilder booleanBuilder = new BooleanBuilder();
    if (searchName != null && !searchName.isEmpty())
      booleanBuilder.and(food.name.contains(searchName));

    if (until != null)
      booleanBuilder.and(food.close.goe(until));

    if (minPrice != null)
      booleanBuilder.and(food.price.goe(minPrice));

    if (maxPrice != null)
      booleanBuilder.and(food.price.goe(maxPrice));

    foodJPQLQuery.where(booleanBuilder);

    JPQLQuery<FoodViewCompactDTO> bigQuery = foodJPQLQuery.select(
      Projections.bean(
        FoodViewCompactDTO.class,
        food.id,
        food.name,
        food.description,
        food.price,
        food.stock,
        food.opened,
        food.close,
        food.registrar,
        food.added,
        food.updated,
        foodReview.count().as("reviewCount"),
        foodReview.rating.avg().as("avgRate")
      )
    );

    if (minRate != null) {
      bigQuery.having(foodReview.rating.avg().goe(minRate));
    }

    this.getQuerydsl().applyPagination(pageable, bigQuery);

    List<FoodViewCompactDTO> list = bigQuery.fetch();
    long count = bigQuery.fetchCount();

    return new PageImpl<>(list, pageable, count);
  }

  @Override
  public Page<FoodViewDTO> getFoodsImageSupport(Pageable pgb, String searchName, Long minPrice, Long maxPrice, Integer minRate, LocalDateTime until) {

    QFood food = QFood.food;
    QFoodReview foodReview = QFoodReview.foodReview;

    JPQLQuery<Food> foodJPQLQuery = from(food);
    foodJPQLQuery.leftJoin(foodReview).on(foodReview.food.eq(food));

    BooleanBuilder booleanBuilder = new BooleanBuilder();
    if (searchName != null && !searchName.isEmpty())
      booleanBuilder.and(food.name.contains(searchName));

    if (until != null)
      booleanBuilder.and(food.close.goe(until));

    if (minPrice != null)
      booleanBuilder.and(food.price.goe(minPrice));

    if (maxPrice != null)
      booleanBuilder.and(food.price.goe(maxPrice));

    foodJPQLQuery.where(booleanBuilder);


    foodJPQLQuery.groupBy(food);

    this.getQuerydsl().applyPagination(pgb, foodJPQLQuery);

    JPQLQuery<Tuple> bigQuery = foodJPQLQuery.select(food, foodReview.countDistinct(), foodReview.rating.avg());

    List<Tuple> tuples = bigQuery.fetch();

    List<FoodViewDTO> dtoList = tuples.stream().map(tuple -> {
      Food food1 = tuple.get(food);
      long reviewCount = tuple.get(1, Long.class);
      Double avgRate = tuple.get(2, Double.class);

      return FoodViewDTO.of(food1, reviewCount, avgRate);
    }).toList();

    long totalCount = bigQuery.fetchCount();
    return new PageImpl<>(dtoList, pgb, totalCount);
  }
}
