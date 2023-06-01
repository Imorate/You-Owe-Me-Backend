package ir.imorate.yom.security.service.impl;

import ir.imorate.yom.security.entity.Role;
import ir.imorate.yom.security.entity.RoleType;
import ir.imorate.yom.security.entity.User;
import ir.imorate.yom.security.exception.ResourceNotFoundException;
import ir.imorate.yom.security.mapper.UserMapper;
import ir.imorate.yom.security.repository.UserRepository;
import ir.imorate.yom.security.request.CreateUserRequest;
import ir.imorate.yom.security.service.RoleService;
import ir.imorate.yom.security.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;
    private final RoleService roleService;

    @Override
    public User findUser(String username) {
        return userRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    @Transactional
    public void createUser(CreateUserRequest request, RoleType roleType) {
        Role role = roleService.findRole(roleType);
        User user = userMapper.toUser(request);
        String password = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRoles(Set.of(role));
        userRepository.save(user);
        log.info("User with username '{}' created", user.getUsername());
    }

    @Override
    public void enableUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
        log.info("User with username '{}' enabled", user.getUsername());
    }

}
