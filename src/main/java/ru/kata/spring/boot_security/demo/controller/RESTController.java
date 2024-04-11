package ru.kata.spring.boot_security.demo.controller;


import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class RESTController {
    private final UserService userService;
    private final RoleService roleService;

    public RESTController(UserServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/users/{id}")
    public User getUser(@PathVariable long id) {
        return userService.findUserById(id);
    }

    @GetMapping(value = "/users")
    public List<User> getAllUsers() {
        return userService.allUsers();
    }

    @GetMapping(value = "/roles")
    public List<Role> getAllRoles() {
        return roleService.allRoles();
    }

    @PostMapping(value = "/users")
    public User addUser(@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }
}
