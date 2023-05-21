package ir.imorate.yom.security.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Role entity test")
class RoleTest {

    @Test
    @DisplayName("Role test")
    void testRole() {
        RoleType adminRoleType = RoleType.ADMIN;
        String description = "Role description";
        Role role = new Role()
                .setId(1)
                .setType(adminRoleType)
                .setDescription(description);

        assertThat(role.getId()).isEqualTo(1);
        assertThat(role.getType()).isEqualTo(adminRoleType);
        assertThat(role.getType().getTitle()).isEqualTo("Admin");
        assertThat(role.getDescription()).isEqualTo(description);
    }

    @Test
    @DisplayName("Role toString() method")
    void testToStringRole() {
        RoleType userRoleType = RoleType.USER;
        String description = "Role description";
        Role role = new Role()
                .setId(2)
                .setType(userRoleType)
                .setDescription(description);

        assertThat(role.getId()).isEqualTo(2);
        assertThat(role.getType()).isEqualTo(userRoleType);
        assertThat(role.getType().getTitle()).isEqualTo("User");
        assertThat(role.getDescription()).isEqualTo(description);

        String expectedToString = String.format("Role(id=%d, type=%s, description=%s)",
                role.getId(),
                role.getType(),
                role.getDescription());

        assertThat(role.toString()).isEqualTo(expectedToString);
    }

}