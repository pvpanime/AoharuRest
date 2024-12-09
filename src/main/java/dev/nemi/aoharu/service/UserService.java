package dev.nemi.aoharu.service;


import dev.nemi.aoharu.dto.SignupDTO;

public interface UserService {
  public static class UseridTakenException extends RuntimeException {}
  void insertUser(SignupDTO signupDTO);
}
