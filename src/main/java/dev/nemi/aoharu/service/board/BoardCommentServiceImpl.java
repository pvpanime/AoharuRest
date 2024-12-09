package dev.nemi.aoharu.service.board;

import dev.nemi.aoharu.dto.PageRequestDTO;
import dev.nemi.aoharu.dto.PageResponseDTO;
import dev.nemi.aoharu.dto.board.BoardCommentEditDTO;
import dev.nemi.aoharu.dto.board.BoardCommentViewDTO;
import dev.nemi.aoharu.dto.board.BoardCommentWriteDTO;
import dev.nemi.aoharu.prime.Board;
import dev.nemi.aoharu.prime.BoardComment;
import dev.nemi.aoharu.repository.board.BoardCommentRepo;
import dev.nemi.aoharu.repository.board.BoardRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardCommentServiceImpl implements BoardCommentService {

  private final BoardRepo boardRepo;
  private final BoardCommentRepo boardCommentRepo;
  private final ModelMapper modelMapper;

  @Override
  public Long add(BoardCommentWriteDTO dto) {
    BoardComment boardComment = modelMapper.map(dto, BoardComment.class);
    if (boardComment.getBoard() == null) {
      Board theBoard = boardRepo.findById(dto.getBid()).orElseThrow();
      boardComment.setBoard(theBoard);
    }
    log.info("boardComment = {}", boardComment);
    log.info("automatically mapped? : {}", boardComment.getBoard());
    Long cid = boardCommentRepo.save(boardComment).getCid();

    return cid;
  }

  @Override
  public void modify(Long cid, BoardCommentEditDTO dto) {
    BoardComment boardComment = boardCommentRepo.findById(cid).orElseThrow();
    boardComment.editPayload(dto.getContent());
    boardCommentRepo.save(boardComment);
  }

  @Override
  public PageResponseDTO<BoardCommentViewDTO> getCommentsOf(Long bid, PageRequestDTO requestDTO) {
    Pageable pgb = PageRequest.of(requestDTO.getPage() <= 0 ? 0 : requestDTO.getPage() - 1, requestDTO.getSize(), Sort.by("cid").ascending());
    Page<BoardComment> result = boardCommentRepo.getCommentsOfBoard(bid, pgb);
    PageResponseDTO<BoardCommentViewDTO> response = PageResponseDTO.<BoardCommentViewDTO>withAll()
      .pageRequestDTO(requestDTO)
      .dtoList(result.getContent().stream().map(boardComment -> modelMapper.map(boardComment, BoardCommentViewDTO.class)).toList())
      .total(result.getTotalElements())
      .build();
    return response;
  }

  @Override
  public void delete(Long cid) {
    boardCommentRepo.deleteById(cid);
  }

}
