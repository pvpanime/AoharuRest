package dev.nemi.aoharu.dto.board;

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
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardPageRequestDTO implements PageRequestDTO {

  private static final int DEFAULT_PAGE_SIZE = 10;
  public static final String DEFAULT_SIZE_STR = DEFAULT_PAGE_SIZE + "";
  public static final BoardPageRequestDTO DEFAULT = BoardPageRequestDTO.builder().build();

  @Min(1)
  @Positive
  @Builder.Default
  private int page = 1;

  @Min(1)
  @Max(100)
  @Positive
  @Builder.Default
  private int size = DEFAULT_PAGE_SIZE;

  private String[] searchFor;

  private String search;

  public boolean isSearchingFor(String s) {
    if (searchFor == null || s == null) {
      return false;
    }
    for (String searchItem : searchFor) {
      if (s.equals(searchItem)) {
        return true;
      }
    }
    return false;
  }

  public Pageable getPageable(String... props) {
    return PageRequest.of(this.page - 1, this.size, Sort.by(props).descending());
  }

  public List<Pair<String, String>> useParams(int page) {
    List<Pair<String, String>> params = new ArrayList<>();
    if (page > 1) params.add(Pair.of("page", Integer.toString(page)));

    if (size != DEFAULT_PAGE_SIZE) params.add(Pair.of("size", Integer.toString(size)));

    if (searchFor != null && search != null && !search.isEmpty()) {
      for (String s : searchFor) {
        if (s != null && !s.isEmpty()) params.add(Pair.of("searchFor", s));
      }
      params.add(Pair.of("search", search));
    }
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
