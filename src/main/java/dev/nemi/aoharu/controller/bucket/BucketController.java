package dev.nemi.aoharu.controller.bucket;

import dev.nemi.aoharu.dto.InsertResultDTO;
import dev.nemi.aoharu.dto.PageResponseDTO;
import dev.nemi.aoharu.dto.RestResponseDTO;
import dev.nemi.aoharu.dto.bucket.BucketCreateDTO;
import dev.nemi.aoharu.dto.bucket.BucketEditDTO;
import dev.nemi.aoharu.dto.bucket.BucketPageRequestDTO;
import dev.nemi.aoharu.service.bucket.BucketService;
import dev.nemi.aoharu.dto.bucket.BucketViewDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
public class BucketController {

  private final BucketService bucketService;

  @GetMapping("/api/bucket/list")
  public ResponseEntity<PageResponseDTO<BucketViewDTO>> getListOf(
    @Valid @ModelAttribute("requestDTO") BucketPageRequestDTO pageRequestDTO,
    BindingResult br
  ) throws BindException {
    if (br.hasErrors()) throw new BindException(br);
    PageResponseDTO<BucketViewDTO> dto = bucketService.getListOf(pageRequestDTO);
    return ResponseEntity.ok(dto);
  }

  @GetMapping("/api/bucket/{id}")
  public ResponseEntity<RestResponseDTO<BucketViewDTO>> getOne(
    @PathVariable Long id
  ) {
    BucketViewDTO bucket = bucketService.getOne(id);
    return RestResponseDTO.ok(bucket);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/api/bucket/create")
  public ResponseEntity<RestResponseDTO<InsertResultDTO>> create(
    @AuthenticationPrincipal UserDetails userDetails,
    @Valid BucketCreateDTO bucketCreateDTO,
    BindingResult br
  ) throws BindException {
    if (br.hasErrors()) throw new BindException(br);
    bucketCreateDTO.setUserid(userDetails.getUsername());
    Long id = bucketService.create(bucketCreateDTO);
    return RestResponseDTO.ok(new InsertResultDTO(id));
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/api/bucket/edit")
  public ResponseEntity<RestResponseDTO.Void> edit(
    @AuthenticationPrincipal UserDetails userDetails,
    @Valid BucketEditDTO bucketEditDTO,
    BindingResult br
  ) throws BindException {
    if (br.hasErrors()) throw new BindException(br);
    bucketService.update(bucketEditDTO, userDetails.getUsername());
    return RestResponseDTO.Void.ok("");
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping("/api/bucket/{id}")
  public ResponseEntity<RestResponseDTO.Void> delete(
    @AuthenticationPrincipal UserDetails userDetails,
    @PathVariable Long id
  ) {
    bucketService.delete(id, userDetails.getUsername());
    return RestResponseDTO.Void.ok("");
  }
}


//@Controller
//@RequiredArgsConstructor
//public class BucketController {
//
//  private final BucketService bucketService;
//
//
//  @GetMapping("/bucket/view/{id}")
//  public String view(
//    @PathVariable long id,
//    @Valid @ModelAttribute("requestDTO") BucketPageRequestDTO pageRequestDTO,
//    BindingResult br,
//    Model model
//  ) {
//    if (br.hasErrors()) { return "redirect:/bucket/view/"+id; }
//    BucketViewDTO bucket = bucketService.getOne(id);
//    model.addAttribute("bucket", bucket);
//    model.addAttribute("deleteAction", "/bucket/delete/"+id);
//    return "bucket/view";
//  }
//
//}
