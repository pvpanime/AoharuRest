package dev.nemi.aoharu.prime;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Board extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long bid;

  @Column(length = 128, nullable = false)
  private String title;

  @Column(length = 2048, nullable = false)
  private String content;

  @Column(length = 32, nullable = false)
  private String userid;

  @Builder.Default
  @Column(precision = 2, nullable = false)
  private Integer status = 1;

  public void editPayload(String title, String content) {
    this.title = title;
    this.content = content;
  }
}
