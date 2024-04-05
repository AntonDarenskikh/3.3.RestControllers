package ru.kata.spring.boot_security.demo.repository;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

/*    @Override
    public User findByUsername(String username) {
        User user = null;
        Set<Role> roles = null;
        try {
            user = entityManager.createQuery("select u from User u where u.username = :username" , User.class)
                    .setParameter("username", username).getSingleResult();
            Hibernate.initialize(user.getRoles());
        } catch (NoResultException e) {
        }
        return user;
    }*/

    @Override
    public User findByEmail(String email) {
        User user = null;
        Set<Role> roles = null;
        try {
            user = entityManager.createQuery("select u from User u where u.email = :email" , User.class)
                    .setParameter("email", email).getSingleResult();
            Hibernate.initialize(user.getRoles());
        } catch (NoResultException e) {
        }
        return user;
    }


    @Override
    public Optional<User> findById(long userId) {
        User user = null;
        try {
            user = entityManager.createQuery("select u from User u where u.id = :id" , User.class)
                    .setParameter("id", userId).getSingleResult();
        } catch (NoResultException e) {
        }
        return Optional.of(user);
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u" , User.class).getResultList();
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteById(long id) {
        User user = null;
        try {
            user = entityManager.createQuery("select u from User u where u.id = :id" , User.class)
                    .setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
        }
        entityManager.remove(user);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }
}
