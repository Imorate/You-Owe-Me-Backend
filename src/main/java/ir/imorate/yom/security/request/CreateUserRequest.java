package ir.imorate.yom.security.request;

public record CreateUserRequest(
        String username,
        String password,
        String email
) {
}
