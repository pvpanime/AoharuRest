package dev.nemi.aoharu.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import dev.nemi.aoharu.prime.Bucket;
import dev.nemi.aoharu.prime.QBucket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("unused")
public class BucketSearchImpl extends QuerydslRepositorySupport implements BucketSearch {

  public BucketSearchImpl() {
    super(Bucket.class);
  }

  @Override
  public Page<Bucket> search(Pageable pageable, String[] searchFor, String search, int[] statusOf, LocalDateTime dueStart, LocalDateTime dueEnd) {
    QBucket bucket = QBucket.bucket;
    JPQLQuery<Bucket> query = from(bucket);

    if (searchFor != null && searchFor.length > 0 && search != null && !search.isEmpty()) {
      BooleanBuilder bb = new BooleanBuilder();
      for (String sFor : searchFor) {
        switch (sFor) {
          case "title":
            bb.or(bucket.title.contains(search));
            break;
          case "description":
            bb.or(bucket.description.contains(search));
            break;
        }
      }

      query.where(bb);
    }

    if (statusOf != null && statusOf.length > 0) {
      BooleanBuilder bb = new BooleanBuilder();
      for (int s : statusOf) {
        bb.or(bucket.status.eq(s));
      }
      query.where(bb);
    }

    if (dueStart != null) query.where(bucket.dueTo.goe(dueStart));
    if (dueEnd != null) query.where(bucket.dueTo.loe(dueEnd));

    this.getQuerydsl().applyPagination(pageable, query);


    List<Bucket> buckets = query.fetch();
    long total = query.fetchCount();
    return new PageImpl<>(buckets, pageable, total);
  }
}
