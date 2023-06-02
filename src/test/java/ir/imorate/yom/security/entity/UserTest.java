package ir.imorate.yom.security.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User entity test")
class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .username("test")
                .email("test@gmail.com")
                .password("123456")
                .build();
    }

    @Test
    @DisplayName("User initial fields value")
    void shouldFieldsValueBeValid() {
        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("test");
        assertThat(user.getPassword()).isEqualTo("123456");
        assertThat(user.getEmail()).isEqualTo("test@gmail.com");
        assertThat(user.getEnabled()).isFalse();
        assertThat(user.getAccountNonLocked()).isTrue();
        assertThat(user.getAccountNonExpired()).isTrue();
        assertThat(user.getCredentialsNonExpired()).isTrue();
    }

    @Test
    @DisplayName("User role values and sizes")
    void shouldRolesMatchExpectedValuesAndSizes() {
        assertThat(user.getRoles()).isNull();

        Role userRole = new Role()
                .setType(RoleType.USER);
        Role adminRole = new Role()
                .setType(RoleType.ADMIN);
        user.setRoles(Set.of(userRole));

        assertThat(user.getRoles())
                .hasSize(1)
                .contains(userRole)
                .doesNotContain(adminRole);

        user.setRoles(Set.of(userRole, adminRole));

        assertThat(user.getRoles())
                .hasSize(2)
                .contains(userRole, adminRole);
    }

    @Test
    @DisplayName("User empty role")
    void shouldRoleSetBeEmpty() {
        user.setRoles(Collections.emptySet());

        assertThat(user.getRoles())
                .hasSize(0)
                .isNullOrEmpty();
    }

}