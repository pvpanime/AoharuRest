package dev.nemi.aoharu.service.bucket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BucketViewDTO {
  private Long id;
  private String title;
  private String description;
  private LocalDateTime dueTo;
  private String userid;
  private Integer status;
  private LocalDateTime added;
  private LocalDateTime updated;

  public String getBadge() {
    if (status == 0 && dueTo.isBefore(LocalDateTime.now())) return "expired";
    return switch (status) {
      case 1 -> "finished";
      case -1 -> "dropped";
      default -> "";
    };
  }
}
