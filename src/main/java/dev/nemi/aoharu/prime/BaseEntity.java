package dev.nemi.aoharu.prime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = { AuditingEntityListener.class })
@Getter
@ToString
public abstract class BaseEntity {

  @CreatedDate
  @Column(name = "added", updatable = false)
  private LocalDateTime added;

  @LastModifiedDate
  @Column(name = "updated")
  private LocalDateTime updated;
}
