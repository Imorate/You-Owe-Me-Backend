package ir.imorate.yom.security.exception;

public class UserIsEnabledException extends RuntimeException {

    public UserIsEnabledException(String message) {
        super(message);
    }
}
