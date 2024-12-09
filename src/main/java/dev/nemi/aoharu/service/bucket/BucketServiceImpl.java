package dev.nemi.aoharu.service.bucket;

import dev.nemi.aoharu.dto.PageResponseDTO;
import dev.nemi.aoharu.prime.Bucket;
import dev.nemi.aoharu.repository.BucketRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BucketServiceImpl implements BucketService {

  private final BucketRepo repo;
  private final ModelMapper modelMapper;

  @Override
  public Long create(BucketCreateDTO dto) {
    Bucket bucket = modelMapper.map(dto, Bucket.class);
    Long id = repo.save(bucket).getId();
    return id;
  }

  @Override
  public BucketViewDTO getOne(Long id) {
    Bucket bucket = repo.findById(id).orElseThrow();
    BucketViewDTO dto = modelMapper.map(bucket, BucketViewDTO.class);
    return dto;
  }

  @Override
  public PageResponseDTO<BucketViewDTO> getListOf(BucketPageRequestDTO dto) {
    Page<Bucket> page = repo.search(dto.getDefaultPageable(), dto.getSearchFor(), dto.getSearch(), dto.getStatusOf(), dto.getDueStart(), dto.getDueEnd());
    List<BucketViewDTO> list = page.getContent().stream().map(bucket -> modelMapper.map(bucket, BucketViewDTO.class)).toList();

    return PageResponseDTO.<BucketViewDTO>withAll()
      .pageRequestDTO(dto)
      .dtoList(list)
      .total(page.getTotalElements())
      .build();
  }

  @Override
  public void update(BucketEditDTO dto) {
    Bucket bucket = repo.findById(dto.getId()).orElseThrow();
    bucket.update(dto.getTitle(), dto.getDescription(), dto.getDueTo(), dto.getStatus());
    repo.save(bucket);
  }

  @Override
  public void delete(Long id) {
    repo.deleteById(id);
  }

}
