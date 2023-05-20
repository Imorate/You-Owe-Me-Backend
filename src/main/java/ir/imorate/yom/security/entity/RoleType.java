package ir.imorate.yom.security.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {

    USER("User"),
    ADMIN("Admin");

    private final String title;

}