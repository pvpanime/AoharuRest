package dev.nemi.aoharu.prime;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

  @Id
  private String userid;

  private String passwd;

  private String email;

  private boolean deleted;

  @Setter
  private Integer social;

  @ElementCollection(fetch = FetchType.LAZY)
  @Builder.Default
  private Set<Role> roles = new HashSet<>();

  public void updatePassword(String passwd) {
    this.passwd = passwd;
  }

  public void updateEmail(String email) {
    this.email = email;
  }

  public void updateDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public void addRole(Role role) {
    roles.add(role);
  }

  public void clearRoles() {
    roles.clear();
  }

}
