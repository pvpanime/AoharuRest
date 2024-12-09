package dev.nemi.aoharu.repository.users;

import dev.nemi.aoharu.prime.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {

  @EntityGraph(attributePaths = "roles")
  @Query("select u from User u where u.userid = :userid and (u.social = 0 or u.social is null)")
  Optional<User> findByUserid(String userid);

}
