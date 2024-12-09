package dev.nemi.aoharu.repository.board;

import dev.nemi.aoharu.prime.BoardComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardCommentRepo extends JpaRepository<BoardComment, Long> {

  @Query("select c from BoardComment c where c.board.bid = :bid")
  Page<BoardComment> getCommentsOfBoard(long bid, Pageable pageable);

  @Query("delete from BoardComment bc where bc.board.bid = :bid")
  void deleteCommentsOfBoard(long bid);
}
