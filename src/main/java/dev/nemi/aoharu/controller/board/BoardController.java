package dev.nemi.aoharu.controller.board;

import dev.nemi.aoharu.dto.board.*;
import dev.nemi.aoharu.dto.PageResponseDTO;
import dev.nemi.aoharu.service.board.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @GetMapping("/board")
  public String boardIndex(
    @Valid @ModelAttribute("requestDTO") BoardPageRequestDTO pageRequestDTO,
    BindingResult pageBR,
    Model model
  ) {
    if (pageBR.hasErrors()) { return "redirect:/board"; }
    PageResponseDTO<BoardListViewDTO> dto = boardService.getList(pageRequestDTO);
    model.addAttribute("dto", dto);
    return "board/index";
  }

  @GetMapping("/board/view/{id}")
  public String view(
    @PathVariable long id,
    @AuthenticationPrincipal UserDetails userDetails,
    @Valid @ModelAttribute("requestDTO") BoardPageRequestDTO pageRequestDTO,
    BindingResult pageBR,
    Model model
    ) {
    if (pageBR.hasErrors()) { return "redirect:/board/view/"+id; }

    log.info("userDetails: {}", userDetails);

    BoardViewDTO board = boardService.getOne(id);
    model.addAttribute("board", board);
    model.addAttribute("owner", userDetails != null && userDetails.getUsername().equals(board.getUserid()));
    model.addAttribute("deleteAction", "/board/delete/"+id);
    return "board/view";
  }



  @PreAuthorize("isAuthenticated()")
  @GetMapping("/board/write")
  public String writeView(
    Model model
  ) {
    model.addAttribute("useEdit", false);
    model.addAttribute("useTitle", "Write");
    model.addAttribute("useAction", "/board/write");
    model.addAttribute("board", BoardWriteDTO.EMPTY);
    return "board/edit";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/board/write")
  public String write(
    @AuthenticationPrincipal UserDetails userDetails,
    @Valid BoardWriteDTO boardWriteDTO,
    BindingResult boardBR,
    RedirectAttributes ra
  ) {
    if (boardBR.hasErrors()) {
      ra.addFlashAttribute("board", boardWriteDTO);
      ra.addFlashAttribute("invalid", boardBR.getAllErrors());
      return "redirect:/board/write";
    }

    if (boardWriteDTO.getUserid() == null) boardWriteDTO.setUserid(userDetails.getUsername());
    Long id = boardService.write(boardWriteDTO);
    if (id != null) {
      return "redirect:/board/view/"+id;
    } else {
      throw new RuntimeException("Failed to write");
    }
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/board/edit/{id}")
  public String editView(
    @AuthenticationPrincipal UserDetails userDetails,
    @Valid @ModelAttribute("requestDTO") BoardPageRequestDTO pageRequestDTO,
    BindingResult pageBR,
    @PathVariable long id,
    Model model
  ) {
    if (pageBR.hasErrors()) return "redirect:/board/edit/"+id;

    BoardViewDTO board = boardService.getOneWithOwnership(userDetails.getUsername(), id);
    if (board == null) throw new AccessDeniedException("Access denied");

    model.addAttribute("useEdit", true);
    model.addAttribute("useTitle", "Edit");
    model.addAttribute("useAction", "/board/edit");
    model.addAttribute("board", board);
    return "board/edit";
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/board/edit")
  public String edit(
    @AuthenticationPrincipal UserDetails userDetails,
    @ModelAttribute("requestDTO") BoardPageRequestDTO pageRequestDTO,
    @Valid BoardEditDTO boardEditDTO,
    BindingResult boardBR,
    RedirectAttributes ra
  ) {
    if (boardBR.hasErrors()) {
      List<ObjectError> allErrors = boardBR.getAllErrors();
      ra.addFlashAttribute("invalid", allErrors);
      return "redirect:/board/edit/"+boardEditDTO.getBid() + pageRequestDTO.useQuery();
    }
    BoardViewDTO board = boardService.getOneWithOwnership(userDetails.getUsername(), boardEditDTO.getBid());
    if (board == null) throw new AccessDeniedException("Access denied");
    boardService.edit(boardEditDTO);
    return "redirect:/board/view/"+boardEditDTO.getBid() + pageRequestDTO.useQuery();
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/board/delete/{id}")
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
