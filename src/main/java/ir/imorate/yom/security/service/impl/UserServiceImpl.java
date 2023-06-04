package ir.imorate.yom.security.service.impl;

import ir.imorate.yom.security.entity.Role;
import ir.imorate.yom.security.entity.RoleType;
import ir.imorate.yom.security.entity.User;
import ir.imorate.yom.security.exception.RoleNotFoundException;
import ir.imorate.yom.security.exception.UserExistsException;
import ir.imorate.yom.security.exception.UserIsEnabledException;
import ir.imorate.yom.security.exception.UserNotFoundException;
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

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "User service")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;
    private final RoleService roleService;

    @Override
    public Optional<User> findUser(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    @Override
    public Optional<User> findUser(long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public void createUser(CreateUserRequest request, RoleType roleType) {
        String username = request.username();
        Role role = roleService.findRole(roleType)
                .orElseThrow(() -> new RoleNotFoundException(roleType.getTitle()));
        findUser(username).ifPresent(user1 -> {
            throw new UserExistsException(username);
        });
        User createdUser = userMapper.toUser(request);
        String password = bCryptPasswordEncoder.encode(createdUser.getPassword());
        createdUser.setPassword(password);
        createdUser.setRoles(Set.of(role));
        userRepository.save(createdUser);
        Set<String> createdUserRoles = mapRolesToStringSet(createdUser.getRoles());
        log.info("User with username '{}' and roles {} created", createdUser.getUsername(), createdUserRoles);
    }

    @Override
    public void enableUser(long id) {
        String userId = String.valueOf(id);
        User user = findUser(id)
                .orElseThrow(() -> new UserNotFoundException(userId));
        if (user.getEnabled()) {
            throw new UserIsEnabledException(userId);
        }
        user.setEnabled(true);
        userRepository.save(user);
        log.info("User with username '{}' enabled", user.getUsername());
    }

    private Set<String> mapRolesToStringSet(Set<Role> roleSet) {
        return roleSet
                .stream()
                .map(r -> r.getType().getTitle())
                .collect(Collectors.toSet());
    }

}
