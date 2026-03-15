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
import org.example.kinobe.model.Movie;
import org.example.kinobe.model.User;
import org.example.kinobe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRestController {



    @Autowired
    UserService userService;

    public UserRestController(UserService userService){this.userService=userService;}


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User userCreated = userService.createuser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
    @GetMapping
    public ResponseEntity<List<User>>readAllUsers(){
        List<User> users =userService.readAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> readUserById(@PathVariable int userId){
        User user = userService.readUsersById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable int userId,@RequestBody User user){
        user.setUserId(userId);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);

    }

   @DeleteMapping("/{userId}")
   public ResponseEntity<Void> deleteUser(@PathVariable int userId){
        User user = userService.readUsersById(userId);
        userService.deleteUser(user);
        return ResponseEntity.noContent().build();
   }



//    @GetMapping("/login")
//    public String login(HttpSession session) {
//        if (session.getAttribute("user") != null) {
//            return "redirect:/user/profile";
//        }
//
//        return "login";
//    }


    @GetMapping("/profile")
    public ResponseEntity<?> profile(HttpSession session){
        User user = (User) session.getAttribute("user");

        if (user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        if (user.getUsername().equals("August") && user.getPassword().equals("Admin")){
            List<User> users =userService.readAllUsers();
            ResponseEntity.ok(users);
        }
        return ResponseEntity.ok(user);
    }


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
