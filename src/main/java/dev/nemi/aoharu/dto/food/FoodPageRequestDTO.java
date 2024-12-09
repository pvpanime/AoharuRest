package dev.nemi.aoharu.dto.food;

import dev.nemi.aoharu.dto.PageRequestDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodPageRequestDTO implements PageRequestDTO {

  private static final int DEFAULT_PAGE_SIZE = 16;
  public static final String DEFAULT_SIZE_STR = DEFAULT_PAGE_SIZE + "";
  public static final FoodPageRequestDTO DEFAULT = FoodPageRequestDTO.builder().build();

  @Min(1)
  @Positive
  @Builder.Default
  private int page = 1;

  @Min(1)
  @Max(100)
  @Positive
  @Builder.Default
  private int size = DEFAULT_PAGE_SIZE;

  private String searchName;

  private Long minPrice;
  private Long maxPrice;
  private Integer minRate;
  private LocalDateTime until;

  public Pageable getPageable(String... props) {
    return PageRequest.of(this.page - 1, this.size, Sort.by(props));
  }

  public List<Pair<String, String>> useParams(int page) {
    List<Pair<String, String>> params = new ArrayList<>();
    if (page > 1) params.add(Pair.of("page", Integer.toString(page)));

    if (size != DEFAULT_PAGE_SIZE) params.add(Pair.of("size", Integer.toString(size)));

    if (searchName != null && !searchName.isEmpty()) {
      params.add(Pair.of("searchName", searchName));
    }

    if (minPrice != null) params.add(Pair.of("minPrice", minPrice.toString()));
    if (maxPrice != null) params.add(Pair.of("maxPrice", maxPrice.toString()));
    if (minRate != null) params.add(Pair.of("minRate", Integer.toString(minRate)));
    if (until != null) params.add(Pair.of("until", until.toString()));
    return params;
  }

  public List<Pair<String, String>> useParams() {
    return useParams(page);
  }


  public String usePage(int page) {
    StringBuilder sb = new StringBuilder();
    for (Pair<String, String> p : useParams(page)) {
      if (!sb.isEmpty()) sb.append("&");
      sb.append(p.getFirst()).append("=").append(URLEncoder.encode(p.getSecond(), StandardCharsets.UTF_8));
    }
    return sb.toString();
  }

  public String useQuery(int page) {
    String s = this.usePage(page);
    if (s.isEmpty()) return "";
    return "?" + s;
  }

  public String useQuery() {
    return useQuery(page);
  }
}
