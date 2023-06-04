package ir.imorate.yom.security.service;

import ir.imorate.yom.security.entity.RoleType;
import ir.imorate.yom.security.entity.User;
import ir.imorate.yom.security.request.CreateUserRequest;

import java.util.Optional;

public interface UserService {

    Optional<User> findUser(String username);

    Optional<User> findUser(long id);

    void createUser(CreateUserRequest request, RoleType roleType);

    void enableUser(long id);

}
