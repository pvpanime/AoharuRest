package dev.nemi.aoharu.service.bucket;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BucketCreateDTO {
  private String title;
  private String description;
  private Integer status;
  private String userid;
  private LocalDateTime dueTo;
}
