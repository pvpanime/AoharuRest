package dev.nemi.aoharu.repository;

import dev.nemi.aoharu.prime.Bucket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface BucketSearch {
  Page<Bucket> search(Pageable pageable, String[] searchFor, String search, int[] statusOf, LocalDateTime dueStart, LocalDateTime dueEnd);
}
