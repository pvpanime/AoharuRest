package dev.nemi.aoharu.prime;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "food_review", indexes = { @Index(name = "index_food_review", columnList = "food_id") })
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "food")
public class FoodReview extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 500, nullable = false)
  private String comment;

  @Column(nullable = false)
  @Min(1)
  @Max(5)
  private int rating;

  @Column(length = 32, nullable = false)
  private String userid;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  private Food food;

  public void update(String comment, int rating) {
    this.comment = comment;
    this.rating = rating;
  }

}
