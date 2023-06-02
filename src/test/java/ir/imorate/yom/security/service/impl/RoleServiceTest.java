package ir.imorate.yom.security.service.impl;

import ir.imorate.yom.security.entity.Role;
import ir.imorate.yom.security.entity.RoleType;
import ir.imorate.yom.security.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

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
    void shouldFindRole() {
        when(roleRepository.findByType(RoleType.USER))
                .thenReturn(Optional.of(role));

        Optional<Role> optionalRole = roleService.findRole(RoleType.USER);

        assertTrue(optionalRole.isPresent());
        assertRoleFields(optionalRole.get());
        verify(roleRepository, times(1)).findByType(RoleType.USER);
    }

    private void assertRoleFields(Role role) {
        assertThat(role).isNotNull();
        assertThat(role.getId()).isEqualTo(this.role.getId());
        assertThat(role.getType()).isEqualTo(this.role.getType());
        assertThat(role.getType().getTitle()).isEqualTo(this.role.getType().getTitle());
        assertThat(role.getDescription()).isEqualTo(this.role.getDescription());
    }

}