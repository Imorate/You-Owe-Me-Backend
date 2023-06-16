package ir.imorate.yom.security.service.impl;

import ir.imorate.yom.security.entity.Role;
import ir.imorate.yom.security.entity.RoleType;
import ir.imorate.yom.security.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("User details service test")
class UserDetailsServiceTest {

    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Role role = new Role()
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
    @DisplayName("Load user by username")
    void shouldLoadUserByUsername() {
        String username = "test";
        when(userService.findUser(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertThat(userDetails.getUsername()).isEqualTo(username);
        assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());
        assertThat(userDetails.isEnabled()).isEqualTo(user.getEnabled());
        assertThat(userDetails.isAccountNonExpired()).isEqualTo(user.getAccountNonExpired());
        assertThat(userDetails.isAccountNonLocked()).isEqualTo(user.getAccountNonLocked());
        assertThat(userDetails.isCredentialsNonExpired()).isEqualTo(user.getCredentialsNonExpired());

        List<String> roleList = user.getRoles().stream().map(role -> role.getType().getTitle()).toList();

        assertThat(userDetails.getAuthorities())
                .hasSize(1)
                .extracting(GrantedAuthority::getAuthority)
                .isEqualTo(roleList);

        verify(userService).findUser(
                argThat(arg -> arg.equals(username))
        );
    }

    @Test
    @DisplayName("Load user that doesn't exists")
    void shouldThrowExceptionWithUnknownUser() {
        String username = "unknown";

        when(userService.findUser(username)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class);
    }

}