package dev.nemi.aoharu.controller.board;

import dev.nemi.aoharu.dto.board.BoardCommentPageRequestDTO;
import dev.nemi.aoharu.dto.PageResponseDTO;
import dev.nemi.aoharu.dto.board.BoardCommentEditDTO;
import dev.nemi.aoharu.service.board.BoardCommentService;
import dev.nemi.aoharu.dto.board.BoardCommentViewDTO;
import dev.nemi.aoharu.dto.board.BoardCommentWriteDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class BoardCommentApiController {

  private final BoardCommentService commentService;

  @Tag(name = "add comment")
  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Long>> add(
    @AuthenticationPrincipal UserDetails userDetails,
    @Valid @RequestBody BoardCommentWriteDTO commentDTO,
    BindingResult bindingResult
  ) throws BindException {
    log.info("Received comment: {}", commentDTO);
    if (bindingResult.hasErrors()) {
      throw new BindException(bindingResult);
    }
    commentDTO.setUserid(userDetails.getUsername());
    Long cid = commentService.add(commentDTO);

    Map<String, Long> responseBody = Map.of("commentId", cid, "success", 1L);
    return ResponseEntity.ok(responseBody);
  }

  @Tag(name = "get comments for specified board id")
  @GetMapping(value = "/list/{bid}")
  public ResponseEntity<PageResponseDTO<BoardCommentViewDTO>> list(@PathVariable Long bid, BoardCommentPageRequestDTO pageRequest) {
    PageResponseDTO<BoardCommentViewDTO> responseDTO = commentService.getCommentsOf(bid, pageRequest);
    return ResponseEntity.ok(responseDTO);
  }

  @Tag(name = "update comment")
  @PutMapping(value = "/{cid}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, String>> update(@PathVariable Long cid, @RequestBody BoardCommentEditDTO commentDTO) {
    commentService.modify(cid, commentDTO);
    return ResponseEntity.ok(Map.of("success", "1"));
  }

  @Tag(name = "Delete comment")
  @DeleteMapping(value = "/{cid}")
  public ResponseEntity<Map<String, String>> delete(@PathVariable Long cid) {
    commentService.delete(cid);
    return ResponseEntity.ok(Map.of("success", "1"));
  }
}
