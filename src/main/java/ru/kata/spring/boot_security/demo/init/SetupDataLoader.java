package ru.kata.spring.boot_security.demo.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final UserService userService;


    private final RoleService roleService;

    public SetupDataLoader(UserServiceImpl userService, UserRepository userRepository, RoleRepository roleRepository, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }


        roleService.createRoleIfNotFound("ROLE_ADMIN");
        roleService.createRoleIfNotFound("ROLE_USER");

        Role adminRole = roleService.findByRole("ROLE_ADMIN");
        Role userRole = roleService.findByRole("ROLE_USER");

        if (userService.findUserByUsername("admin") == null) {
            User admin = new User("admin", "password", Set.of(adminRole, userRole));
            userService.saveUser(admin);
        }

        if (userService.findUserByUsername("user") == null) {
            User startUser = new User("user", "password",Set.of(userRole));
            userService.saveUser(startUser);
        }

        alreadySetup = true;
    }
}
