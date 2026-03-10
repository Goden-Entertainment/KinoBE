package org.example.kinobe.controller;

import jakarta.servlet.http.HttpSession;
import org.example.kinobe.exception.DatabaseOperationException;
import org.example.kinobe.model.User;
import org.example.kinobe.repository.UserRepository;
import org.example.kinobe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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



//    @GetMapping("/login")
//    public String login(HttpSession session) {
//        if (session.getAttribute("user") != null) {
//            return "redirect:/user/profile";
//        }
//
//        return "login";
//    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestParam("username") String username,
                                              @RequestParam("password") String password,
                                              HttpSession session) {
        try {
            User user = userService.login(username, password);

            if (user != null) {
                session.setAttribute("user", user);
                return ResponseEntity.ok(user);
            }

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");

        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Database error at authentication", e);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }

}
