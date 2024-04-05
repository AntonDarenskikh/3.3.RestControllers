package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    //User findByUsername(String username);

    User findByEmail(String email);

    Optional<User> findById(long userId);
    List<User> findAll();
    void save(User user);
    void deleteById(long id);


    void update(User user);
}
