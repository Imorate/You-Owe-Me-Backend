package ir.imorate.yom.security.entity;

import net.datafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("User entity test")
class UserTest {

    @Autowired
    private Faker faker;

    @Test
    @DisplayName("User initial values")
    void testInitialValuesForUser() {
        String userName = faker.name().username();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 256, true, true, true);

        User user = User.builder()
                .username(userName)
                .password(password)
                .email(email)
                .build();

        assertThat(user.getUsername()).isEqualTo(userName);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getEnabled()).isFalse();
        assertThat(user.getAccountNonLocked()).isTrue();
        assertThat(user.getAccountNonExpired()).isTrue();
        assertThat(user.getCredentialsNonExpired()).isTrue();
    }

    @Test
    @DisplayName("User toString() method")
    void testToStringUser() {
        String userName = faker.name().username();
        String email = faker.internet().emailAddress();

        User user = User.builder()
                .username(userName)
                .email(email)
                .build();

        String expectedToString = String.format("User(id=null, username=%s, email=%s, accountNonExpired=%b," +
                        " accountNonLocked=%b, credentialsNonExpired=%b, enabled=%b)",
                userName,
                email,
                user.getAccountNonExpired(),
                user.getAccountNonLocked(),
                user.getCredentialsNonExpired(),
                user.getEnabled());

        assertThat(user.toString()).isEqualTo(expectedToString);
    }

    @Test
    @DisplayName("User roles")
    void testUserRoles() {
        String userName = faker.name().username();
        String email = faker.internet().emailAddress();

        User user = User.builder()
                .username(userName)
                .email(email)
                .build();

        assertThat(user.getRoles()).isNull();

        Role userRole = new Role().setType(RoleType.USER);
        Role adminRole = new Role().setType(RoleType.ADMIN);
        user.setRoles(Set.of(userRole));

        assertThat(user.getRoles())
                .hasSize(1)
                .contains(userRole)
                .doesNotContain(adminRole);

        user.setRoles(Set.of(userRole, adminRole));

        assertThat(user.getRoles())
                .hasSize(2)
                .contains(userRole, adminRole);

        user.setRoles(Collections.emptySet());

        assertThat(user.getRoles())
                .hasSize(0)
                .isNullOrEmpty();
    }
}