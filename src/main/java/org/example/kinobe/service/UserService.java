package org.example.kinobe.service;

import org.example.kinobe.model.User;

import java.util.List;

public interface UserService  {
    User createuser(User user);
    List<User> getAllUser();
    User updateUser(int userId, User user);
    void deleteUser(int userId);




}
