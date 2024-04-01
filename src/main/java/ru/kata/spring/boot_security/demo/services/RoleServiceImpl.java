package ru.kata.spring.boot_security.demo.services;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<Role> allRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Role findByRole(String role) {
        return roleRepository.findByRole(role);
    }

    @Override
    @Transactional
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean setRoles(User user) {
        Set<Role> roles = user.getRoles().stream().map(Role::getRole)
                .map(roleRepository::findByRole).collect(Collectors.toSet());
        user.setRoles(roles);
        return true;
    }


    @Transactional
    public Role createRoleIfNotFound(String name) {
        Role role = roleRepository.findByRole(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }
}
