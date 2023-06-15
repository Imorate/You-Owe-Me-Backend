package ir.imorate.yom.security.service.impl;

import ir.imorate.yom.security.entity.Role;
import ir.imorate.yom.security.entity.RoleType;
import ir.imorate.yom.security.repository.RoleRepository;
import ir.imorate.yom.security.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> findRole(RoleType roleType) {
        return roleRepository.findByType(roleType);
    }

    @Override
    public Set<String> toStringSet(Set<Role> roleSet) {
        return roleSet
                .stream()
                .map(r -> r.getType().getTitle())
                .collect(Collectors.toSet());
    }

}
