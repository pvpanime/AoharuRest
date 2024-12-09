package dev.nemi.aoharu.prime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "food")
public class FoodImage implements Comparable<FoodImage> {

  @Id
  private String id;

  private String filename;

  private int ordinal;

  @ManyToOne
  private Food food;

  @Override
  public int compareTo(FoodImage o) {
    return this.ordinal - o.ordinal;
  }

  public void changeFood(Food food) {
    this.food = food;
  }

}
