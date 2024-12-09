package dev.nemi.aoharu.dto.board;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardWriteDTO {
  public static final BoardWriteDTO EMPTY = BoardWriteDTO.builder().title("").content("").build();

  @NotBlank
  private String title;

  @NotBlank
  private String content;
  private String userid;

  @Builder.Default
  private Integer status = 1;

}
