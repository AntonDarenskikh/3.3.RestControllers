package ru.kata.spring.boot_security.demo.repository;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Role role) {
        entityManager.persist(role);
    }

    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("from Role" , Role.class).getResultList();
    }

    @Override
    public Role findByRole(String name) {
        Role role = null;
        try {
            role = entityManager.createQuery("from Role where name = :name" , Role.class)
                    .setParameter("name", name).getSingleResult();
        } catch (NoResultException e) {
        }
        return role;
    }

}
