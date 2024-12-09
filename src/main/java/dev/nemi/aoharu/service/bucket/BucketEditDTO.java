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
public class BucketEditDTO {
  private Long id;
  private String title;
  private String description;
  private Integer status;
  private LocalDateTime dueTo;
}
