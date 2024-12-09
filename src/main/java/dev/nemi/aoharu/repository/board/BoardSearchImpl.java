package dev.nemi.aoharu.repository.board;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import dev.nemi.aoharu.prime.Board;
import dev.nemi.aoharu.prime.QBoard;
import dev.nemi.aoharu.prime.QBoardComment;
import dev.nemi.aoharu.dto.board.BoardListViewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@SuppressWarnings("unused")
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

  public BoardSearchImpl() {
    super(Board.class);
  }

  @Override
  public Page<Board> search(Pageable pageable, String[] searchFor, String search) {
    QBoard board = QBoard.board;
    JPQLQuery<Board> query = this.from(board);

    if (searchFor != null && searchFor.length > 0 && search != null && !search.isEmpty()) {

      BooleanBuilder bb = new BooleanBuilder();

      for (String sFor : searchFor) {
        switch (sFor) {
          case "title":
            bb.or(board.title.contains(search));
            break;
          case "content":
            bb.or(board.content.contains(search));
            break;
          case "user":
            bb.or(board.userid.contains(search));
            break;
        }
      }

      query.where(bb);
    }

    this.getQuerydsl().applyPagination(pageable, query);
    List<Board> boards = query.fetch();

    long count = query.fetchCount();

    return new PageImpl<>(boards, pageable, count);
  }





  @Override
  public Page<BoardListViewDTO> searchAsDTO(Pageable pageable, String[] searchFor, String search) {

    QBoard board = QBoard.board;
    QBoardComment comment = QBoardComment.boardComment;

    JPQLQuery<Board> query = this.from(board);

    query.leftJoin(comment).on(comment.board.eq(board));
    query.groupBy(board);


    if (searchFor != null && searchFor.length > 0 && search != null && !search.isEmpty()) {

      BooleanBuilder bb = new BooleanBuilder();

      for (String sFor : searchFor) {
        switch (sFor) {
          case "title":
            bb.or(board.title.contains(search));
            break;
          case "content":
            bb.or(board.content.contains(search));
            break;
          case "user":
            bb.or(board.userid.contains(search));
            break;
        }
      }

      query.where(bb);
    }



    JPQLQuery<BoardListViewDTO> dtoQuery = query.select(
      Projections.bean(BoardListViewDTO.class, board.bid, board.title, board.userid, board.added, comment.count().as("commentCount"))
    );

    this.getQuerydsl().applyPagination(pageable, dtoQuery);

    List<BoardListViewDTO> boardListViewDTOS = dtoQuery.fetch();
    long count = dtoQuery.fetchCount();

    return new PageImpl<>(boardListViewDTOS, pageable, count);
  }
}
