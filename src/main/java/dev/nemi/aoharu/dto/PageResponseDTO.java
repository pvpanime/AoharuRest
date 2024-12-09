package dev.nemi.aoharu.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<T> {
  private static final int PAGINATION_WINDOW_SIZE = 10;
  private int page;
  private int size;

  /** total count of board for corresponding request (NOT pages) */
  private long total;

  private int start;
  private int end;

  /** the last page */
  private int last;

  private List<T> dtoList;

  @Builder(builderMethodName = "withAll")
  public PageResponseDTO(
    PageRequestDTO pageRequestDTO,
    List<T> dtoList,
    long total
  ) {
    if (total <= 0) return;
    this.page = pageRequestDTO.getPage();
    this.size = pageRequestDTO.getSize();
    this.total = total;
    this.dtoList = dtoList;

    this.last = (int) Math.ceil((double)total/size);

    if (PAGINATION_WINDOW_SIZE >= last) {
      this.start = 1;
      this.end = last;
    } else {
      int expandRight = (PAGINATION_WINDOW_SIZE / 2);
      int expandLeft = PAGINATION_WINDOW_SIZE - expandRight - 1;

      int pressureRight = page + expandRight > last ? (page + expandRight - last) : 0;
      int pressureLeft = page - expandLeft < 1 ? (expandLeft - page + 1) : 0;

      this.end = Math.min(page + expandRight + pressureLeft, last);

      this.start = Math.max(page - expandLeft - pressureRight, 1);

      if (this.start <= 1 + 2) this.start = 1;
      if (this.end >= this.last - 2) this.end = this.last;

    }

  }
}
