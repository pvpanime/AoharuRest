package dev.nemi.aoharu.controller;

import dev.nemi.aoharu.service.bucket.BucketPageRequestDTO;
import dev.nemi.aoharu.dto.PageResponseDTO;
import dev.nemi.aoharu.service.bucket.BucketCreateDTO;
import dev.nemi.aoharu.service.bucket.BucketEditDTO;
import dev.nemi.aoharu.service.bucket.BucketService;
import dev.nemi.aoharu.service.bucket.BucketViewDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class BucketController {

  private final BucketService bucketService;

  @GetMapping("/bucket")
  public String bucket(
    @Valid @ModelAttribute("requestDTO") BucketPageRequestDTO pageRequestDTO,
    BindingResult br,
    Model model
  ) {
    if (br.hasErrors()) { return "redirect:/bucket"; }

    PageResponseDTO<BucketViewDTO> dto = bucketService.getListOf(pageRequestDTO);
    model.addAttribute("dto", dto);

    return "bucket/index";
  }

  @GetMapping("/bucket/view/{id}")
  public String view(
    @PathVariable long id,
    @Valid @ModelAttribute("requestDTO") BucketPageRequestDTO pageRequestDTO,
    BindingResult br,
    Model model
  ) {
    if (br.hasErrors()) { return "redirect:/bucket/view/"+id; }
    BucketViewDTO bucket = bucketService.getOne(id);
    model.addAttribute("bucket", bucket);
    model.addAttribute("deleteAction", "/bucket/delete/"+id);
    return "bucket/view";
  }

  @GetMapping("/bucket/create")
  public String createView(
    Model model
  ) {
    model.addAttribute("useEdit", false);
    model.addAttribute("useTitle", "Write");
    model.addAttribute("useAction", "/bucket/create");
    return "bucket/edit";
  }

  @PostMapping("/bucket/create")
  public String create(
    @Valid BucketCreateDTO bucketCreateDTO,
    BindingResult br,
    RedirectAttributes ra
  ) {
    if (br.hasErrors()) {
      ra.addFlashAttribute("bucket", bucketCreateDTO);
      ra.addFlashAttribute("invalid", br.getAllErrors());
      return "redirect:/bucket/create";
    }
    if (bucketCreateDTO.getUserid() == null) bucketCreateDTO.setUserid("hina");
    Long id = bucketService.create(bucketCreateDTO);
    if (id != null) {
      return "redirect:/bucket/view/"+id;
    } else {
      throw new RuntimeException("Failed to write");
    }
  }

  @GetMapping("/bucket/edit/{id}")
  public String editView(
    @Valid @ModelAttribute("requestDTO") BucketPageRequestDTO pageRequestDTO,
    BindingResult pageBR,
    @PathVariable long id,
    Model model
  ) {
    if (pageBR.hasErrors()) return "redirect:/bucket/edit/"+id;

    BucketViewDTO bucket = bucketService.getOne(id);
    model.addAttribute("useEdit", true);
    model.addAttribute("useTitle", "Edit");
    model.addAttribute("useAction", "/bucket/edit");
    model.addAttribute("bucket", bucket);
    return "bucket/edit";
  }

  @PostMapping("/bucket/edit")
  public String edit(
    @ModelAttribute("requestDTO") BucketPageRequestDTO pageRequestDTO,
    @Valid BucketEditDTO bucketEditDTO,
    BindingResult br,
    RedirectAttributes ra
  ) {
    if (br.hasErrors()) {
      ra.addFlashAttribute("invalid", br.getAllErrors());
      return "redirect:/bucket/edit/"+bucketEditDTO.getId() + pageRequestDTO.useQuery();
    }
    bucketService.update(bucketEditDTO);
    return "redirect:/bucket/view/"+bucketEditDTO.getId() + pageRequestDTO.useQuery();
  }

  @PostMapping("/bucket/delete/{id}")
  public String delete(
    @PathVariable long id
  ) {
    bucketService.delete(id);
    return "redirect:/bucket";
  }
}
