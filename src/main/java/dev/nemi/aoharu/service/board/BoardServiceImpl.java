package dev.nemi.aoharu.service.board;


import dev.nemi.aoharu.dto.PageResponseDTO;
import dev.nemi.aoharu.dto.board.*;
import dev.nemi.aoharu.prime.Board;
import dev.nemi.aoharu.repository.board.BoardRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

  private final ModelMapper modelMapper;
  private final BoardRepo boardRepo;

  @Override
  public Long write(BoardWriteDTO dto) {
    Board board = modelMapper.map(dto, Board.class);
    Long id = boardRepo.save(board).getBid();
    return id;
  }

  @Override
  public BoardViewDTO getOne(Long id) {
    Optional<Board> result = boardRepo.findById(id);
    Board board = result.orElseThrow();
    BoardViewDTO dto = modelMapper.map(board, BoardViewDTO.class);
    return dto;
  }

  @Override
  public PageResponseDTO<BoardListViewDTO> getList(BoardPageRequestDTO pageRequestDTO) {
    Page<BoardListViewDTO> page = boardRepo.searchAsDTO(pageRequestDTO.getPageable("bid"), pageRequestDTO.getSearchFor(), pageRequestDTO.getSearch());
    return PageResponseDTO.<BoardListViewDTO>withAll()
      .pageRequestDTO(pageRequestDTO)
      .dtoList(page.getContent())
      .total(page.getTotalElements())
      .build();
  }

  @Override
  public void edit(BoardEditDTO dto) {
    Optional<Board> result = boardRepo.findById(dto.getBid());
    Board board = result.orElseThrow();
    board.editPayload(dto.getTitle(), dto.getContent());
    boardRepo.save(board);
  }

  @Override
  public void delete(Long id) {
    boardRepo.deleteById(id);
  }

  @Override
  public BoardViewDTO getOneWithOwnership(String userid, Long boardId) {
    Optional<Board> board = boardRepo.findByBidAndUserid(boardId, userid);
    if (!board.isPresent()) return null;

    BoardViewDTO dto = modelMapper.map(board.get(), BoardViewDTO.class);
    return dto;
  }
}
