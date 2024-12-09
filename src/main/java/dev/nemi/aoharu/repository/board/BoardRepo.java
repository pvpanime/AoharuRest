package dev.nemi.aoharu.repository.board;

import dev.nemi.aoharu.prime.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepo extends JpaRepository<Board, Long>, BoardSearch {

  Optional<Board> findByBidAndUserid(Long bid, String userid);
}
