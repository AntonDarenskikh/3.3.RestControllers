package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User findUserById(Long userId);
    List<User> allUsers();
    User findUserByEmail(String email);
    void saveUser(User user);
    void deleteUser(Long id);

    void updateUser(User user);

}
