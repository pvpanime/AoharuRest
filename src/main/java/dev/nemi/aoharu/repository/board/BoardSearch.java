package dev.nemi.aoharu.repository.board;

import dev.nemi.aoharu.prime.Board;
import dev.nemi.aoharu.dto.board.BoardListViewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {
  Page<Board> search(Pageable pageable, String[] searchFor, String search);

  Page<BoardListViewDTO> searchAsDTO(Pageable pageable, String[] searchFor, String search);
}
