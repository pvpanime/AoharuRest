package dev.nemi.aoharu.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadResultDTO {

  private String id;

  private String name;

  private String mime;

  @JsonProperty("link")
  public String getLink() {
    if (mime != null && mime.startsWith("image/")) {
      return "/th/"+ id +"_"+ name;
    } else {
      return "/i/"+ id +"_"+ name;
    }
  }
}
