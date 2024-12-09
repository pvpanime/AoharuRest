package dev.nemi.aoharu;

import dev.nemi.aoharu.dto.PageResponseDTO;
import dev.nemi.aoharu.service.board.BoardService;
import dev.nemi.aoharu.service.bucket.BucketPageRequestDTO;
import dev.nemi.aoharu.service.bucket.BucketService;
import dev.nemi.aoharu.service.bucket.BucketViewDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

@Log4j2
@SpringBootTest
class AoharuTests {

  @Autowired
  private BoardService boardService;

  @Autowired
  private BucketService bucketService;

//  @Test
//  public void selectTest() {
//    Optional<Board> board = boardRepo.findById(250L);
//    Assertions.assertTrue(board.isPresent());
//    log.info("Board={}", board.get());
//  }
//
//  @Test
//  public void updateTest() {
//    Optional<Board> found = boardRepo.findById(250L);
//    Assertions.assertTrue(found.isPresent());
//    Board board = found.get();
//    board.update("Hina chaan", "kawaii");
//    boardRepo.save(board);
//  }

//  @Test
//  public void paginationTest() {
//    Pageable pageable = PageRequest.of(0, 10, Sort.by("updated").descending());
//    Page<Board> boardPage = boardRepo.findAll(pageable);
//    logPage(boardPage);
//  }


//  @Test
//  public void insertBucketTest() {
//    IntStream.rangeClosed(1, 50).forEach(i -> {
//      Random random = new Random();
//      long anySecond = random.nextLong(1800L, 200000L);
//      LocalDateTime ldt = LocalDateTime.now().plusSeconds(anySecond);
//      Bucket bucket = Bucket.builder().title("BucketList").description("This should be done by " + ldt).dueTo(ldt).userid("hina").status(0).build();
//      bucketRepo.save(bucket);
//    });
//  }
//
//  @Test
//  public void selectBucketTest() {
//    Optional<Bucket> found = bucketRepo.findById(1L);
//    Bucket bucket = found.orElseThrow();
//    log.info("Bucket={}", bucket);
//  }
//
//  @Test
//  public void updateBucketTest() {
//    Optional<Bucket> found = bucketRepo.findById(1L);
//    Bucket bucket = found.orElseThrow();
//    bucket.update("Trip", "Go trip!", LocalDateTime.now(), bucket.getStatus());
//    bucketRepo.save(bucket);
//    log.info("Bucket={}", bucket);
//  }


  private <T> void logPage(Page<T> pg) {
    log.info("total count = {}", pg.getTotalElements());
    log.info("total pages = {}", pg.getTotalPages());
    log.info("page number = {}", pg.getNumber());
    log.info("page size = {}", pg.getSize());
    pg.forEach(log::info);
  }

//  @Test
//  public void bucketPageTest() {
//    Pageable pageable = PageRequest.of(0, 10, Sort.by("dueTo").ascending());
//    Page<Bucket> bucketPage = bucketRepo.findAll(pageable);
//
//    logPage(bucketPage);
//  }
//
//  @Test
//  public void repoJpaTest() {
//    Pageable pgb = PageRequest.of(0, 10, Sort.by("added").descending());
//    Page<Bucket> page = bucketRepo.findByTitleContainingOrderByAddedDesc("Tri", pgb);
//
//    logPage(page);
//  }
//
//  @Test
//  public void repoAnnoTest() {
//    Pageable pgb = PageRequest.of(0, 10, Sort.by("added").descending());
//    Page<Bucket> page = bucketRepo.endsWith("1", pgb);
//    logPage(page);
//  }

//  @Test
//  public void querydslTest() {
//    Pageable pgb = PageRequest.of(0, 10, Sort.by("added").descending());
//    Page<Board> result = boardRepo.realSearch(
//      pgb, new String[] {"title", "content"},
//      "4"
//    );
//
//    logPage(result);
//  }

//  @Test
//  public void bucketQuerydslTest() {
//    Pageable pgb = PageRequest.of(0, 10, Sort.by("added").descending());
//    Page<Bucket> result = bucketRepo.search(
//      pgb, new String[] { "title", "description" }, "12"
//    );
//
//    logPage(result);
//  }

//
//  @Test
//  public void boardWriteTest() {
//    BoardWriteDTO dto = BoardWriteDTO.builder()
//      .title("The 2nd Querldsl-ed Board title")
//      .content("This is now Spring Boot, Second time!")
//      .userid("hina")
//      .status(1)
//      .build();
//
//    Long id = boardService.write(dto);
//    log.info("BoardId={}", id);
//  }
//
//  @Test
//  public void boardViewTest() {
//    BoardViewDTO dto = boardService.getOne(102L);
//    log.info("BoardView={}", dto);
//  }
//
//
//  @Test
//  public void boardDeleteTest() {
//    boardService.delete(1L);
//  }

  @Test
  public void bucketListTest() {
    log.info("BucketList={}", BucketPageRequestDTO.DEFAULT);
    Assertions.assertNotNull(BucketPageRequestDTO.DEFAULT);
    PageResponseDTO<BucketViewDTO> responseDTO = bucketService.getListOf(BucketPageRequestDTO.DEFAULT);
    log.info("BucketList={}", responseDTO);
  }


}
