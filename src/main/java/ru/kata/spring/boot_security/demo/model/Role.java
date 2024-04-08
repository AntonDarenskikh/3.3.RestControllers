package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String role) {
        this.role = role;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Role(String role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }




    @Override
    public String getAuthority() {
        return role;
    }

}
