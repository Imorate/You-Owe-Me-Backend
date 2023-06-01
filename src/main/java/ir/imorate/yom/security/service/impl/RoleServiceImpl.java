package ir.imorate.yom.security.service.impl;

import ir.imorate.yom.security.entity.Role;
import ir.imorate.yom.security.entity.RoleType;
import ir.imorate.yom.security.exception.ResourceNotFoundException;
import ir.imorate.yom.security.repository.RoleRepository;
import ir.imorate.yom.security.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findRole(RoleType roleType) {
        return roleRepository.findByType(roleType)
                .orElseThrow(ResourceNotFoundException::new);
    }

}