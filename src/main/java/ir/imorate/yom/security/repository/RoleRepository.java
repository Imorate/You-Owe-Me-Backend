package ir.imorate.yom.security.repository;

import ir.imorate.yom.security.entity.Role;
import ir.imorate.yom.security.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByType(@NonNull RoleType type);

}