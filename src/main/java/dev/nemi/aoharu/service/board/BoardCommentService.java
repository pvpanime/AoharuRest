package dev.nemi.aoharu.service.board;

import dev.nemi.aoharu.dto.PageRequestDTO;
import dev.nemi.aoharu.dto.PageResponseDTO;
import dev.nemi.aoharu.dto.board.BoardCommentEditDTO;
import dev.nemi.aoharu.dto.board.BoardCommentViewDTO;
import dev.nemi.aoharu.dto.board.BoardCommentWriteDTO;

public interface BoardCommentService {
  Long add(BoardCommentWriteDTO dto);

  void modify(Long cid, BoardCommentEditDTO dto);

  PageResponseDTO<BoardCommentViewDTO> getCommentsOf(Long bid, PageRequestDTO requestDTO);

  void delete(Long cid);
}
