package ir.imorate.yom.security.service;

import ir.imorate.yom.security.entity.Role;
import ir.imorate.yom.security.entity.RoleType;

public interface RoleService {

    Role findRole(RoleType roleType);

}
