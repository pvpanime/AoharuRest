package dev.nemi.aoharu.prime;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.repository.EntityGraph;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "images")
public class Food extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 128, nullable = false)
  private String name;

  @Column(length = 2048, nullable = false)
  private String description;

  @Column(nullable = false)
  private Long price;

  @Column(nullable = false)
  private Long stock;

  @Column(nullable = false)
  private LocalDateTime opened;

  @Column(nullable = false)
  private LocalDateTime close;

  @Column(length = 32, nullable = false)
  private String registrar;

  @OneToMany(mappedBy = "food", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true)
  @Builder.Default
  @BatchSize(size = 20)
  private Set<FoodImage> images = new HashSet<>();

  public void addImage(String id, String filename) {
    FoodImage image = FoodImage.builder().id(id).filename(filename).food(this).ordinal(images.size()).build();
    images.add(image);
  }

  public void clearImages() {
    images.forEach(im -> im.changeFood(null));
    images.clear();
  }

  public void edit(String name, String description, Long price, Long stock, LocalDateTime opened, LocalDateTime close) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.stock = stock;
    this.opened = opened;
    this.close = close;
  }

}
