package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleService {
    Role createRoleIfNotFound(String name);
    List<Role> allRoles();

    Role findByRole(String role);

    void saveRole(Role role);
}
