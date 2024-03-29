package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

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
    @Transactional
    public Role findByRole(String role) {
        return roleRepository.findByRole(role);
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.saveRole(role);
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
