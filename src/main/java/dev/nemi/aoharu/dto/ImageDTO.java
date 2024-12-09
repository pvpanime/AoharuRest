package dev.nemi.aoharu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
  private String id;
  private String name;
  private int ordinal;

  public String getSrc() {
    return String.format("/i/%s_%s", id, name);
  }

  public String getThumbnail() {
    return String.format("/th/%s_%s", id, name);
  }
}
