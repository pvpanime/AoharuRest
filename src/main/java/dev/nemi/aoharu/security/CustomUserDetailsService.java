package dev.nemi.aoharu.security;

import dev.nemi.aoharu.dto.UserDTO;
import dev.nemi.aoharu.prime.User;
import dev.nemi.aoharu.repository.users.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepo userRepo;

  @Override
  public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
    log.info("loadUserByUsername : {}", userid);
//
//    UserDetails userDetails = User.builder().username("user1")
//      .password(passwordEncoder.encode("ketchup"))
//      .authorities("ROLE_USER").build();
//    return userDetails;
    Optional<User> result = userRepo.findByUserid(userid);

    if (result.isEmpty()) throw new UsernameNotFoundException(userid + " not found");

    User user = result.get();
    UserDTO userDTO = new UserDTO(
      user.getUserid(),
      user.getPasswd(),
      user.getEmail(),
      user.isDeleted(),
      null,
      user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).toList()
    );

    log.info("User Security DTO : {}", userDTO);
    return userDTO;
  }

}
