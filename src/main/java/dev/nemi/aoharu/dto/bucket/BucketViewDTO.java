package dev.nemi.aoharu.dto.bucket;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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


  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime added;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime updated;

  @JsonProperty("badge")
  public String getBadge() {
    if (status == 0 && dueTo.isBefore(LocalDateTime.now())) return "expired";
    return switch (status) {
      case 1 -> "finished";
      case -1 -> "dropped";
      default -> "";
    };
  }
}
