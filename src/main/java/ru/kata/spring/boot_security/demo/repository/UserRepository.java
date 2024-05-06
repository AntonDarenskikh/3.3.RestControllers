package ru.kata.spring.boot_security.demo.repository;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserRepository {

    User findByEmail(String email);

    User findById(long userId);
    List<User> findAll();
    void save(User user);
    void deleteById(long id);


    void update(User user);
}
