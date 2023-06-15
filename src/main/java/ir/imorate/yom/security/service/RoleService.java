package ir.imorate.yom.security.service;

import ir.imorate.yom.security.entity.Role;
import ir.imorate.yom.security.entity.RoleType;

import java.util.Optional;
import java.util.Set;

public interface RoleService {

    Optional<Role> findRole(RoleType roleType);

    Set<String> toStringSet(Set<Role> roleSet);

}
