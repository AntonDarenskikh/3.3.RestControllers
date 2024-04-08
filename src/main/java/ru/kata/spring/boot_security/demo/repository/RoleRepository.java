package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleRepository {
    void save(Role role);
    List<Role> findAll();
    Role findByRole(String name);

}
