package org.example.kinobe.controller;

import jakarta.servlet.http.HttpSession;
import org.example.kinobe.exception.DatabaseOperationException;
import org.example.kinobe.model.User;
import org.example.kinobe.repository.UserRepository;
import org.example.kinobe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;



    @GetMapping("/login")
    public String login(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "redirect:/user/profile";
        }

        return "login";
    }

    @PostMapping("/login")
    public String authenticateUser(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   HttpSession session) {
        try {
            User user = userService.login(username, password);

            if (user != null) {
                session.setAttribute("user", user);
                return "redirect:/user/profile";
            }

        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Database error at authentication", e);
        }

        return "redirect:/user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

}
