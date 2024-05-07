package ru.kata.spring.boot_security.demo.init;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.util.Set;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final UserService userService;
    private final RoleService roleService;
    public SetupDataLoader(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }


        roleService.createRoleIfNotFound("ROLE_ADMIN");
        roleService.createRoleIfNotFound("ROLE_USER");

        Role adminRole = roleService.findByRole("ROLE_ADMIN");
        Role userRole = roleService.findByRole("ROLE_USER");

        if (userService.findUserByEmail("ya@yandex.ru") == null) {
            User admin = new User("ya@yandex.ru", "password", "Alex", "Vans", (byte) 20, Set.of(adminRole, userRole));
            userService.saveUser(admin);
        }

        if (userService.findUserByEmail("user@mail.ru") == null) {
            User startUser = new User("user@mail.ru", "password", "Mike", "Casper", (byte) 30, Set.of(userRole));
            userService.saveUser(startUser);
        }

        alreadySetup = true;
    }
}
