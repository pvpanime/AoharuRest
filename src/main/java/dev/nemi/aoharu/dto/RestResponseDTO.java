package dev.nemi.aoharu.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponseDTO<DT> {
  private boolean success;
  private String message;
  private DT data;

  public static <DT> ResponseEntity<RestResponseDTO<DT>> ok(DT data) {
    return ResponseEntity.ok(RestResponseDTO.<DT>builder().success(true).data(data).build());
  }

  public static <DT> ResponseEntity<RestResponseDTO<DT>> ok(DT data, String message) {
    return ResponseEntity.ok(RestResponseDTO.<DT>builder().success(true).message(message).data(data).build());
  }


  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class Void {
    private boolean success;
    private String message;

    public static ResponseEntity<RestResponseDTO.Void> ok(String message) {
      return ResponseEntity.ok(RestResponseDTO.Void.builder().success(true).message(message).build());
    }
  }
}
