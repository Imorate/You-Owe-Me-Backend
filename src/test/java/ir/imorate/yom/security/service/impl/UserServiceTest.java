package ir.imorate.yom.security.service.impl;

import ir.imorate.yom.security.entity.Role;
import ir.imorate.yom.security.entity.RoleType;
import ir.imorate.yom.security.entity.User;
import ir.imorate.yom.security.exception.ResourceExistsException;
import ir.imorate.yom.security.exception.ResourceNotFoundException;
import ir.imorate.yom.security.exception.UserIsEnabledException;
import ir.imorate.yom.security.exception.UserNotFoundException;
import ir.imorate.yom.security.mapper.UserMapper;
import ir.imorate.yom.security.repository.UserRepository;
import ir.imorate.yom.security.request.CreateUserRequest;
import ir.imorate.yom.security.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("User service test")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserServiceImpl userService;

    @Captor
    private ArgumentCaptor<User> acUser;

    private User user;

    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        role = new Role()
                .setId(1)
                .setType(RoleType.USER)
                .setDescription("User role");
        user = User.builder()
                .id(1L)
                .username("test")
                .password("123456")
                .email("test@mail.com")
                .roles(Set.of(role))
                .build();
    }

    @Test
    @DisplayName("Find user")
    void shouldFindUser() {
        String username = "test";

        when(userRepository.findByUsernameIgnoreCase(user.getUsername())).thenReturn(Optional.of(user));

        Optional<User> optionalUser = userService.findUser(username);

        ArgumentCaptor<String> acUsername = ArgumentCaptor.forClass(String.class);
        assertThat(optionalUser.isPresent()).isTrue();
        assertUserFields(optionalUser.get());
        verify(userRepository, times(1)).findByUsernameIgnoreCase(acUsername.capture());
        assertThat(username).isEqualTo(acUsername.getValue());
    }

    @Test
    @DisplayName("Create user")
    void shouldCreateUser() {
        user.setRoles(Collections.emptySet());
        CreateUserRequest request = new CreateUserRequest("test", "123456", "test@mail.com");
        String encodedPassword = "$2a$10$mpD2XNUhPmpV6y3mJTLN0OYutvaW0Konhgp6nADu6cj5ECN2Elxw.";
        RoleType roleType = RoleType.USER;

        when(roleService.findRole(roleType)).thenReturn(Optional.of(role));
        when(userMapper.toUser(request)).thenReturn(user);
        when(bCryptPasswordEncoder.encode("123456")).thenReturn(encodedPassword);
        when(userService.findUser(request.username())).thenReturn(Optional.empty());

        userService.createUser(request, roleType);

        ArgumentCaptor<RoleType> acRoleType = ArgumentCaptor.forClass(RoleType.class);
        ArgumentCaptor<CreateUserRequest> acRequest = ArgumentCaptor.forClass(CreateUserRequest.class);
        ArgumentCaptor<String> acPassword = ArgumentCaptor.forClass(String.class);
        verify(roleService, times(1)).findRole(acRoleType.capture());
        verify(userMapper, times(1)).toUser(acRequest.capture());
        verify(bCryptPasswordEncoder, times(1)).encode(acPassword.capture());
        verify(userRepository, times(1)).save(acUser.capture());
        assertUserFields(acUser.getValue());
        assertThat(roleType).isEqualTo(acRoleType.getValue());
        assertThat(request).isEqualTo(acRequest.getValue());
        assertThat(request.password()).isEqualTo(acPassword.getValue());
    }

    @Test
    @DisplayName("Create user that exists")
    void shouldThrowExceptionOnCreatingExistingUsername() {
        CreateUserRequest request = new CreateUserRequest("test", "123456", "test@mail.com");
        RoleType roleType = RoleType.USER;

        when(roleService.findRole(roleType)).thenReturn(Optional.of(role));
        when(userService.findUser(request.username())).thenReturn(Optional.of(user));
        assertThrows(ResourceExistsException.class, () -> userService.createUser(request, roleType));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Create user with unknown RoleType")
    void shouldThrowExceptionWhenUserHasUnknownRoleType() {
        //shouldNotCreateUserAndThrowResourceNotFoundForRole
        CreateUserRequest request = new CreateUserRequest("test", "123456", "test@mail.com");

        // This is a default value for RoleType for demonstration unknown value
        RoleType roleType = RoleType.USER;

        when(roleService.findRole(roleType)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.createUser(request, roleType));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Enable user")
    void shouldEnableUser() {
        when(userService.findUser(user.getId())).thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> userService.enableUser(user.getId()));
        verify(userRepository, times(1)).save(acUser.capture());
        assertUserFields(acUser.getValue());
    }

    @Test
    @DisplayName("Enable user that doesn't exists")
    void shouldThrowExceptionWhenUserDoesntExists() {
        long userId = -1;

        when(userService.findUser(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.enableUser(userId));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Enable user that is already enabled")
    void shouldThrowExceptionWhenUserIsEnabled() {
        user.setEnabled(true);
        when(userService.findUser(user.getId())).thenReturn(Optional.of(user));
        assertThrows(UserIsEnabledException.class, () -> userService.enableUser(user.getId()));
        verify(userRepository, never()).save(any(User.class));
    }

    private void assertUserFields(User user) {
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(this.user.getId());
        assertThat(user.getUsername()).isEqualTo(this.user.getUsername());
        assertThat(user.getPassword()).isEqualTo(this.user.getPassword());
        assertThat(user.getEmail()).isEqualTo(this.user.getEmail());
        assertThat(user.getRoles())
                .hasSize(1)
                .extracting(Role::getType)
                .contains(RoleType.USER)
                .doesNotContain(RoleType.ADMIN);
    }

}