package dev.nemi.aoharu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * A base implementation of the PageRequestDTO interface providing a simple structure
 * for handling pagination details like page number and size.
 */
@Getter
@Setter
@ToString
public class BasePageRequestDTO implements PageRequestDTO {
    private int page;
    private int size;

    public static BasePageRequestDTO of(int page, int size) {
        BasePageRequestDTO dto = new BasePageRequestDTO();
        dto.setPage(page);
        dto.setSize(size);
        return dto;
    }
}