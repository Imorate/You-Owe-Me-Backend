package ir.imorate.yom.security.mapper;

import ir.imorate.yom.security.entity.User;
import ir.imorate.yom.security.request.CreateUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("User mapper test")
class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("test")
                .email("test@gmail.com")
                .password("123456")
                .build();
    }

    @Test
    @DisplayName("Mapper to user entity")
    void shouldMapperMapsToUser() {
        CreateUserRequest request = null;

        assertThat(userMapper.toUser(request)).isNull();

        request = new CreateUserRequest(user.getUsername(), user.getPassword(), user.getEmail());
        User user = userMapper.toUser(request);

        assertFields(user.getUsername(), user.getPassword(), user.getEmail());
    }

    @Test
    @DisplayName("Mapper to request")
    void shouldMapperMapsToRequest() {
        User nullUser = null;

        assertThat(userMapper.toCreateUserRequest(nullUser)).isNull();

        CreateUserRequest request = userMapper.toCreateUserRequest(user);

        assertFields(request.username(), request.password(), request.email());
    }

    private void assertFields(String username, String password, String email) {
        assertThat(username).isEqualTo(user.getUsername());
        assertThat(password).isEqualTo(user.getPassword());
        assertThat(email).isEqualTo(user.getEmail());
    }

}