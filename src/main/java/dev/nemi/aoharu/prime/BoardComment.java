package dev.nemi.aoharu.prime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "board_comment", indexes = { @Index(name = "index_comment_board", columnList = "board_bid") })
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "board")
//@ToString
public class BoardComment extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cid;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  private Board board;

  @Column(length = 256, nullable = false)
  private String content;

  @Column(length = 32, nullable = false)
  private String userid;

  public void editPayload(String content) {
    this.content = content;
  }
}
