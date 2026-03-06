package org.example.kinobe.controller;

import org.example.kinobe.repository.UserRepository;
import org.example.kinobe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;



}
