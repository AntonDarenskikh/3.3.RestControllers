package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;

@RequestMapping("/admin")
@Controller
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserServiceImpl userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "")
    public String getAdminPage(@ModelAttribute("user") User user, Principal principal, ModelMap model) {
        User thisUser = userService.findUserByEmail(principal.getName());
        model.addAttribute("thisUser", thisUser);
        model.addAttribute("users", userService.allUsers());
        model.addAttribute("allRoles", roleService.allRoles());
        return "admin";
    }


    @PostMapping(value = "/addUser")
    public String addUser(@ModelAttribute("user") User user) {
        roleService.setRoles(user);
        userService.setEncodedPassword(user);

        userService.saveUser(user);
        return "redirect:/admin";
    }


    @PostMapping(value = "/editUser")
    public String editUser(@ModelAttribute("user") User user) {
        roleService.setRoles(user);
        userService.changePasswordIfNew(user);

        userService.updateUser(user);
        return "redirect:/admin";
    }


    @GetMapping(value = "/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
