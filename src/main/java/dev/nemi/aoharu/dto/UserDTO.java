package dev.nemi.aoharu.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@ToString
public class UserDTO extends org.springframework.security.core.userdetails.User {

  private String userid;
  private String passwd;
  private String email;
  private boolean deleted;
  private Integer social;

  public UserDTO(String userid, String passwd, String email, boolean deleted, Integer social, Collection<? extends GrantedAuthority> authorities) {
    super(userid, passwd, authorities);

    this.userid = userid;
    this.passwd = passwd;
    this.email = email;
    this.deleted = deleted;
    this.social = social;
  }
}
