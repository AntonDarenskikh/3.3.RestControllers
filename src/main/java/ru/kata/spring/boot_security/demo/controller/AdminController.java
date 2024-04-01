package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserServiceImpl userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "")
    public String getAdminPage(ModelMap model) {
        model.addAttribute("users", userService.allUsers());
        return "admin";
    }


    @GetMapping(value = "/add")
    public String add(@ModelAttribute("user") User user, ModelMap model) {
        model.addAttribute("allRoles", roleService.allRoles());
        return "addUser";
    }

    @PostMapping(value = "/addUser")
    public String addUser(@ModelAttribute("user") User user) {
        roleService.setRoles(user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin";
    }



    @GetMapping(value = "/edit")
    public String edit(@RequestParam("id") long id, ModelMap model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("allRoles", roleService.allRoles());
        return "editUser";
    }

    @PostMapping(value = "/editUser")
    public String editUser(@ModelAttribute("user") User user) {
        roleService.setRoles(user);

        String oldPassword = userService.findUserById(user.getId()).getPassword();
        String newPassword = user.getPassword();

        //User newUser = userService.changePasswordIfNew(user);
        if (passwordEncoder.matches(newPassword, oldPassword) || oldPassword.equals(newPassword)) {
            user.setPassword(oldPassword);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        userService.updateUser(user);
        return "redirect:/admin";
    }


    @GetMapping(value = "/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
