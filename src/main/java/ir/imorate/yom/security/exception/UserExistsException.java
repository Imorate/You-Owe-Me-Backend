package ir.imorate.yom.security.exception;

public class UserExistsException extends ResourceExistsException {

    public UserExistsException(String message) {
        super(message);
    }

}
