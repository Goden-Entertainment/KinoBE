package org.example.kinobe.service;

import org.example.kinobe.model.User;

import java.util.List;

public interface UserService  {
    User createuser(User user);
    User updateUser(User user);
    void deleteUser(User user);
    List<User> readAllUsers();
    User readUsersById(int userId);
    User login(String username, String password);

}
