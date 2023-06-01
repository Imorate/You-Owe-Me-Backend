package ir.imorate.yom.security.service.impl;

import ir.imorate.yom.security.entity.Role;
import ir.imorate.yom.security.entity.RoleType;
import ir.imorate.yom.security.exception.ResourceNotFoundException;
import ir.imorate.yom.security.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayName("User service test")
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        role = new Role()
                .setId(1)
                .setType(RoleType.USER)
                .setDescription("User role");
    }

    @Test
    @DisplayName("Find role")
    void findRole() {
        when(roleRepository.findByType(RoleType.USER))
                .thenReturn(Optional.of(role));

        Role resultRole = roleService.findRole(RoleType.USER);

        assertRoleFields(resultRole);
    }

    @Test
    @DisplayName("Find role with resource not found exception")
    void testFindRoleNotFound() {
        RoleType roleType = RoleType.USER;

        when(roleRepository.findByType(roleType))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleService.findRole(roleType));
    }

    private void assertRoleFields(Role role) {
        assertThat(role.getId()).isEqualTo(this.role.getId());
        assertThat(role.getType()).isEqualTo(this.role.getType());
        assertThat(role.getType().getTitle()).isEqualTo(this.role.getType().getTitle());
        assertThat(role.getDescription()).isEqualTo(this.role.getDescription());
    }

}