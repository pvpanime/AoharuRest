package dev.nemi.aoharu.prime;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Bucket extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(length = 128, nullable = false)
  private String title;

  @Column(length = 1024, nullable = false)
  private String description;

  @Column(nullable = false)
  private LocalDateTime dueTo;

  @Column(nullable = false)
  private Integer status;

  @Column(length = 32, nullable = false)
  private String userid;

  public void update(String title, String description, LocalDateTime dueTo, Integer status) {
    this.title = title;
    this.description = description;
    this.dueTo = dueTo;
    this.status = status;
  }

}
