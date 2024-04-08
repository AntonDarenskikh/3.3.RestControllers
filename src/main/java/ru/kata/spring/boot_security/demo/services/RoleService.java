package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface RoleService {
    void createRoleIfNotFound(String name);
    List<Role> allRoles();

    Role findByRole(String role);

    void save(Role role);

    void setRoles(User user);
}
