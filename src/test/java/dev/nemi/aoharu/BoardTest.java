package dev.nemi.aoharu;

import dev.nemi.aoharu.dto.BasePageRequestDTO;
import dev.nemi.aoharu.dto.PageResponseDTO;
import dev.nemi.aoharu.dto.board.BoardCommentViewDTO;
import dev.nemi.aoharu.dto.board.BoardCommentWriteDTO;
import dev.nemi.aoharu.dto.board.BoardListViewDTO;
import dev.nemi.aoharu.prime.Board;
import dev.nemi.aoharu.prime.BoardComment;
import dev.nemi.aoharu.repository.board.BoardCommentRepo;
import dev.nemi.aoharu.repository.board.BoardRepo;
import dev.nemi.aoharu.service.board.*;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Log4j2
@SpringBootTest
public class BoardTest {

  @Autowired
  private BoardService boardService;

  @Autowired
  private BoardRepo boardRepo;

  @Autowired
  private BoardCommentRepo boardCommentRepo;

  @Autowired
  private BoardCommentService boardCommentService;

  @Test
  public void addBoardTest() {
//    IntStream.rangeClosed(1, 100).forEach(i -> {
//      Board board = Board.builder().title("Title="+i).content("This is the " + (i * i) + "-th content.").userid("hina").status(1).build();
//      Board result = boardRepo.save(board);
//
//      log.info("Board={}", result.getId());
//    });
  }


  @Test
  public void addBoardCommentFromRepoTest() {
    Board b = boardRepo.findById(2L).orElseThrow();
    BoardComment cResult = boardCommentRepo.save(BoardComment.builder().board(b).userid("hina").content("test").build());
    log.info(cResult);
  }

  @Test
  public void getCommentsOfBoardTest() {
    Pageable pgb = PageRequest.of(0, 10, Sort.by("id").descending());
    Page<BoardComment> comments = boardCommentRepo.getCommentsOfBoard(2L, pgb);
    comments.getContent().forEach(log::info);
  }

  @Test
  @Transactional // <-- Without this, this test fails.
  public void getCommentsWithBoardTest() {
    Pageable pgb = PageRequest.of(0, 10, Sort.by("id").descending());
    Page<BoardComment> comments = boardCommentRepo.getCommentsOfBoard(2L, pgb);
    comments.getContent().forEach(log::info);
    Board b = comments.getContent().get(0).getBoard();
    log.info("Board={}", b);
  }

  @Test
  public void getBoardListTest() {
    Pageable pgb = PageRequest.of(0, 10, Sort.by("id").ascending());
    Page<BoardListViewDTO> boardListView = boardRepo.searchAsDTO(pgb, new String[]{"user"}, "hina");

    boardListView.getContent().forEach(log::info);

  }

  @Test
  public void addCommentTest() {
    Random random = new Random();
    Long bid = 104L;
    BoardCommentWriteDTO commentDTO = BoardCommentWriteDTO.builder()
      .bid(bid)
      .content("comment " + random.nextInt(100000) + " for board " + bid)
      .userid("hina").build();
    Long cid = boardCommentService.add(commentDTO);
    BoardComment comment = boardCommentRepo.findById(cid).orElseThrow();
    log.info("BoardComment={}", comment);
  }

  @Test
  public void getCommentsTest() {
    PageResponseDTO<BoardCommentViewDTO> comments = boardCommentService.getCommentsOf(104L, BasePageRequestDTO.of(0, 10));
    comments.getDtoList().forEach(log::info);
  }


}
