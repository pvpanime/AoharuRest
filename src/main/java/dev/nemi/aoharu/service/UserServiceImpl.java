package dev.nemi.aoharu.service;

import dev.nemi.aoharu.dto.SignupDTO;
import dev.nemi.aoharu.prime.Role;
import dev.nemi.aoharu.prime.User;
import dev.nemi.aoharu.repository.users.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepo userRepo;

  private final PasswordEncoder passwordEncoder;

  @Override
  public void insertUser(SignupDTO signupDTO) {

    String userid = signupDTO.getUserid();
    if (userRepo.existsById(userid)) throw new UseridTakenException();

    User user = User.builder()
      .userid(signupDTO.getUserid())
      .passwd(passwordEncoder.encode(signupDTO.getPassword()))
      .email(signupDTO.getEmail())
      .build();

    user.addRole(Role.USER);

    userRepo.save(user);
  }
}
