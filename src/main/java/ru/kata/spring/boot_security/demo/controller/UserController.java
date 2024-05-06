package ru.kata.spring.boot_security.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String printWelcome(Principal principal, ModelMap model) {

        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("user", user);
/*        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> jsonSend = new HashMap<>();
        jsonSend.put("name", "Test name");
        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonSend);

        String resp = restTemplate.getForObject("http://localhost:8080/api/roles", String.class);
        //String resp1 = restTemplate.postForObject("http", resp, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = null;
        try {
            node = mapper.readTree(resp);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        JsonNode name = node.get(0).get("name");

        System.out.println(resp + "\n нужная часть:" + name);*/
        return "index";
    }

    @GetMapping(value = "/user")
    public String getUserPage(Principal principal, ModelMap model) {
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

}
