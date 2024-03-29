package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;


@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserServiceImpl userService) {
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

}
