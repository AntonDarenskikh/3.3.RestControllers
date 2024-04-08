package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User findUserById(Long userId);
    List<User> allUsers();
    //User findUserByUsername(String username);
    User findUserByEmail(String email);
    void saveUser(User user);
    void deleteUser(Long id);

    void updateUser(User user);

    void changePasswordIfNew(User user);
    void setEncodedPassword(User user);
    String getEncodedPassword(String password);
}
