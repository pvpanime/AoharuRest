package dev.nemi.aoharu.controller;

import dev.nemi.aoharu.service.UploadFileDTO;
import dev.nemi.aoharu.service.UploadResultDTO;
import dev.nemi.aoharu.service.food.FoodService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Log4j2
@RestController
@RequiredArgsConstructor
public class UpDownController {

  private final FoodService foodService;

  @Value("${dev.nemi.aoharu.upload.path}")
  private String uploadPath;

  @Value("${dev.nemi.aoharu.thumbnail.path}")
  private String thumbnailPath;

  private void handleOneFileUpload(MultipartFile file, List<UploadResultDTO> out) {
    String originalFilename = file.getOriginalFilename();
    String uuid = UUID.randomUUID().toString();

    String saveStr = uuid + "_" + originalFilename;
    Path savePath = Paths.get(uploadPath, saveStr);

    try {
      file.transferTo(savePath);
      String mime = Files.probeContentType(savePath);

      if (mime.startsWith("image")) {
        File thumb = new File(thumbnailPath, saveStr);
        Thumbnailator.createThumbnail(savePath.toFile(), thumb, 200, 200);
      }
      out.add(
        UploadResultDTO.builder()
          .id(uuid)
          .name(originalFilename)
          .mime(mime)
          .build()
      );
    } catch (IOException ioe) {
      log.error(ioe);
    }
  }

  @Tag(name = "Upload file")
  @PostMapping(value = "/u/i", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<List<UploadResultDTO>> upload(UploadFileDTO up) {
    List<UploadResultDTO> results = new ArrayList<>();
    if (up.getFiles() != null && !up.getFiles().isEmpty()) {
      up.getFiles().forEach(f -> handleOneFileUpload(f, results));
    }
    return ResponseEntity.ok(results);
  }

  @Tag(name = "view file")
  @GetMapping("/{p:i|th}/{fname}")
  public ResponseEntity<Resource> viewImage(
    @Parameter(
      name = "p",
      description = "\"i\" to be any blob file, \"th\" to be thumbnail file.",
      schema = @Schema(allowableValues = {"i", "th"}))
    @PathVariable String p,
    @PathVariable String fname) {
    String dirname = switch (p) {
      case "i" -> uploadPath;
      case "th" -> thumbnailPath;
      default -> null;
    };
    Resource resource = new FileSystemResource(dirname + File.separator + fname);
    String resourceName = resource.getFilename();
    HttpHeaders headers = new HttpHeaders();
    try {
      headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
    } catch (IOException ioe) {
      log.error(ioe);
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().headers(headers).body(resource);
  }

  @Tag(name = "invoke browser to download the file")
  @GetMapping("/dl/{fname}")
  public ResponseEntity<Resource> download(@PathVariable String fname) {
    Resource resource = new FileSystemResource(uploadPath + File.separator + fname);
    HttpHeaders headers = new HttpHeaders();
    try {
      headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
      headers.add("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"");
    } catch (IOException ioe) {
      log.error(ioe);
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok().headers(headers).body(resource);
  }


  @DeleteMapping("/i/{fname}")
  public Map<String, Boolean> delete(@PathVariable String fname) {
    Resource iresource = new FileSystemResource(uploadPath + File.separator + fname);
    try {
      String contentType = Files.probeContentType(iresource.getFile().toPath());
      boolean removed = iresource.getFile().delete();

      if (contentType.startsWith("image")) {
        File thumb = new File(thumbnailPath, fname);
        thumb.delete();
      }

      return Map.of("success", true, "removed", removed);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
