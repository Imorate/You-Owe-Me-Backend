package ir.imorate.yom.security.repository;

import ir.imorate.yom.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameIgnoreCase(@NonNull String username);

}