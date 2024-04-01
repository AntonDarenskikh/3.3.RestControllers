package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User findUserById(Long userId);
    List<User> allUsers();
    User findUserByUsername(String username);
    boolean saveUser(User user);
    void deleteUser(Long id);

    void updateUser(User user);


    User changePasswordIfNew(User user);
}
