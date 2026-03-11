package org.example.kinobe.controller;

import org.example.kinobe.model.Movie;
import org.example.kinobe.model.User;
import org.example.kinobe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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



}
