package dev.nemi.aoharu.controller.board;


import dev.nemi.aoharu.dto.RestResponseDTO;
import dev.nemi.aoharu.dto.board.*;
import dev.nemi.aoharu.dto.PageResponseDTO;
import dev.nemi.aoharu.service.board.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @GetMapping(value = "/list")
  public ResponseEntity<PageResponseDTO<BoardListViewDTO>> list(
    BoardPageRequestDTO pageRequestDTO
  ) {
    PageResponseDTO<BoardListViewDTO> pageResponseDTO = boardService.getList(pageRequestDTO);
    return ResponseEntity.ok(pageResponseDTO);
  }

  @GetMapping(value = "/view/{boardId}")
  public ResponseEntity<RestResponseDTO<BoardViewDTO>> getOne(@PathVariable long boardId) {
    BoardViewDTO boardViewDTO = boardService.getOne(boardId);
    return RestResponseDTO.ok(boardViewDTO);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/write")
  public ResponseEntity<RestResponseDTO<BoardWriteResponseDTO>> write(
    @AuthenticationPrincipal UserDetails userDetails,
    @Valid @RequestBody BoardWriteDTO boardWriteDTO,
    BindingResult boardBR
  ) throws BindException {
    if (boardBR.hasErrors()) {
      throw new BindException(boardBR);
    }

    if (boardWriteDTO.getUserid() == null)
      boardWriteDTO.setUserid(userDetails.getUsername());
//      boardWriteDTO.setUserid("hina");
    Long id = boardService.write(boardWriteDTO);
    if (id != null) {
      return RestResponseDTO.ok(BoardWriteResponseDTO.builder().boardId(id).build());
    } else {
      throw new RuntimeException("Failed to write");
    }
  }


  @PreAuthorize("isAuthenticated()")
  @PostMapping("/edit")
  public ResponseEntity<RestResponseDTO.Void> edit(
    @AuthenticationPrincipal UserDetails userDetails,
    @Valid BoardEditDTO boardEditDTO,
    BindingResult boardBR
  ) throws BindException {
    if (boardBR.hasErrors()) throw new BindException(boardBR);

    BoardViewDTO board = boardService.getOneWithOwnership(userDetails.getUsername(), boardEditDTO.getBid());
    if (board == null) throw new AccessDeniedException("Access denied");
    boardService.edit(boardEditDTO);
    return RestResponseDTO.Void.ok("");
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/delete/{id}")
  public String delete(
    @AuthenticationPrincipal UserDetails userDetails,
    @PathVariable long id
  ) {

    BoardViewDTO board = boardService.getOne(id);
    if (!board.getUserid().equals(userDetails.getUsername()))
      throw new AccessDeniedException("Access denied");

    boardService.delete(id);
    return "redirect:/board";
  }
}
