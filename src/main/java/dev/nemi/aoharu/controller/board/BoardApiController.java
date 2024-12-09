package dev.nemi.aoharu.controller.board;


import dev.nemi.aoharu.dto.board.BoardPageRequestDTO;
import dev.nemi.aoharu.dto.PageResponseDTO;
import dev.nemi.aoharu.dto.board.BoardListViewDTO;
import dev.nemi.aoharu.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardApiController {

  private final BoardService boardService;

  @GetMapping(value = "/list")
  public ResponseEntity<PageResponseDTO<BoardListViewDTO>> list(
    BoardPageRequestDTO pageRequestDTO
  ) {
    PageResponseDTO<BoardListViewDTO> pageResponseDTO = boardService.getList(pageRequestDTO);
    return ResponseEntity.ok(pageResponseDTO);
  }
}
