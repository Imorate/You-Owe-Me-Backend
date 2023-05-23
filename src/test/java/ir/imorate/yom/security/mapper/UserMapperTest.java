package ir.imorate.yom.security.mapper;

import ir.imorate.yom.security.entity.User;
import ir.imorate.yom.security.request.CreateUserRequest;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("User mapper test")
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Faker faker;

    private String username;

    private String password;

    private String email;

    @BeforeEach
    void setUp() {
        username = faker.name().username();
        email = faker.internet().emailAddress();
        password = faker.internet().password(8, 256, true, true, true);
    }

    @Test
    @DisplayName("toUser() test")
    void toUser() {
        CreateUserRequest request = null;

        assertThat(userMapper.toUser(request)).isNull();

        request = new CreateUserRequest(username, password, email);
        User user = userMapper.toUser(request);

        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getPassword()).isEqualTo(password);
        assertThat(user.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("toCreateUserRequest() test")
    void toCreateUserRequest() {
        User user = null;

        assertThat(userMapper.toCreateUserRequest(user)).isNull();

        user = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
        CreateUserRequest request = userMapper.toCreateUserRequest(user);

        assertThat(request.username()).isEqualTo(username);
        assertThat(request.password()).isEqualTo(password);
        assertThat(request.email()).isEqualTo(email);
    }

}