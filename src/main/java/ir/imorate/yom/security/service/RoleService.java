package ir.imorate.yom.security.service;

import ir.imorate.yom.security.entity.Role;
import ir.imorate.yom.security.entity.RoleType;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findRole(RoleType roleType);

}
