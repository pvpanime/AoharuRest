package dev.nemi.aoharu.repository;

import dev.nemi.aoharu.prime.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepo extends JpaRepository<Bucket, Long>, BucketSearch {
}
