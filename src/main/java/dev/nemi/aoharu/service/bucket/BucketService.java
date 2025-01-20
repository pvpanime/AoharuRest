package dev.nemi.aoharu.service.bucket;

import dev.nemi.aoharu.dto.PageResponseDTO;
import dev.nemi.aoharu.dto.bucket.BucketCreateDTO;
import dev.nemi.aoharu.dto.bucket.BucketEditDTO;
import dev.nemi.aoharu.dto.bucket.BucketPageRequestDTO;
import dev.nemi.aoharu.dto.bucket.BucketViewDTO;

public interface BucketService {
  Long create(BucketCreateDTO dto);

  BucketViewDTO getOne(Long id);

  PageResponseDTO<BucketViewDTO> getListOf(BucketPageRequestDTO dto);

  void update(BucketEditDTO dto, String userid);

  void delete(Long id, String userid);
}
