package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserDetailService;

import java.security.Principal;


@Controller
public class UserController {
    private final UserDetailService userService;

    public UserController(UserDetailService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String printWelcome(ModelMap model) {
        return "index";
    }


    @GetMapping(value = "/user")
    public String getUserPage(Principal principal, ModelMap model) {
        User user = userService.findUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping(value = "/admin")
    public String getAdminPage(ModelMap model) {
        model.addAttribute("users", userService.allUsers());
        return "admin";
    }


    @GetMapping(value = "/admin/add")
    public String add(@ModelAttribute("user") User user, ModelMap model) {
        model.addAttribute("allRoles", userService.allRoles());
        return "addUser";
    }

    @PostMapping(value = "/admin/addUser")
    public String addUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/edit")
    public String edit(@RequestParam("id") long id, ModelMap model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("allRoles", userService.allRoles());
        return "editUser";
    }

    @PostMapping(value = "/admin/editUser")
    public String editUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }


    @GetMapping(value = "/admin/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
