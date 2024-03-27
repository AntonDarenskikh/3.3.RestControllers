package ru.kata.spring.boot_security.demo.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import java.util.Set;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");

        Role adminRole = roleRepository.findByRole("ROLE_ADMIN");
        Role userRole = roleRepository.findByRole("ROLE_USER");

        if (userRepository.findByUsername("admin") == null) {
            User admin = new User("admin", "password", Set.of(adminRole, userRole));
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("user") == null) {
            User startUser = new User("user", "password",Set.of(userRole));
            userRepository.save(startUser);
        }

        alreadySetup = true;
    }

    @Transactional
    public Role createRoleIfNotFound(String name) {
        Role role = roleRepository.findByRole(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }
}
