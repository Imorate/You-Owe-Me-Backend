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
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@DisplayName("Role service test")
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
        RoleType roleType = RoleType.USER;
        when(roleRepository.findByType(roleType)).thenReturn(Optional.of(role));

        Optional<Role> optionalRole = roleService.findRole(roleType);

        assertTrue(optionalRole.isPresent());
        assertRoleFields(optionalRole.get());
        verify(roleRepository).findByType(
                argThat(roleType::equals)
        );
    }

    @Test
    @DisplayName("Find empty optional role")
    void shouldFindEmptyOptionalRole() {
        // Assert that User role is not present
        RoleType roleType = RoleType.USER;
        when(roleRepository.findByType(roleType)).thenReturn(Optional.empty());

        Optional<Role> optionalRole = roleService.findRole(roleType);

        assertFalse(optionalRole.isPresent());
        verify(roleRepository).findByType(
                argThat(roleType::equals)
        );
    }

    @Test
    @DisplayName("Map Role set to String set")
    void shouldReturnStringSetFromRoleSet() {
        Set<Role> roleSet = Set.of(
                new Role().setId(1).setType(RoleType.USER).setDescription("User role"),
                new Role().setId(2).setType(RoleType.ADMIN).setDescription("Admin role")
        );
        Set<String> stringSet = roleService.toStringSet(roleSet);
        assertThat(stringSet)
                .hasSize(2)
                .contains(RoleType.USER.getTitle(), RoleType.ADMIN.getTitle());
    }

    private void assertRoleFields(Role role) {
        assertThat(role).isNotNull();
        assertThat(role.getId()).isEqualTo(this.role.getId());
        assertThat(role.getType()).isEqualTo(this.role.getType());
        assertThat(role.getType().getTitle()).isEqualTo(this.role.getType().getTitle());
        assertThat(role.getDescription()).isEqualTo(this.role.getDescription());
    }

}