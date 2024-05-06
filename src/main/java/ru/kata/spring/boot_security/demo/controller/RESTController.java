package ru.kata.spring.boot_security.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.ExceptionHandler.NoSuchUserException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;
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
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            throw new NoSuchUserException("User with id '" + id + "' not found in DB");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping(value = "/users/current")
    public ResponseEntity<User> getCurrentUser(Principal principal) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserByEmail(principal.getName()));
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.allUsers());
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.status(HttpStatus.OK).body(roleService.allRoles());
    }

    @PostMapping(value = "/users")
    public ResponseEntity<User> addUser(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("ERROR");
        }

        roleService.setRoles(user);
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PatchMapping(value = "/users/{id}")
    public ResponseEntity<User> editUser(@PathVariable long id, @RequestBody User user, BindingResult bindingResult) {
        user.setId(id);
        if (bindingResult.hasErrors()) {
            System.out.println("ERROR");
        }

        roleService.setRoles(user);
        userService.updateUser(user);
        //return user;
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable long id, @RequestBody User user,  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("ERROR");
        }

        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
