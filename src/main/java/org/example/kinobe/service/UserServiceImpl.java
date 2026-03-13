package org.example.kinobe.service;

import org.example.kinobe.exception.ProfileNotFoundException;
import org.example.kinobe.model.User;
import org.example.kinobe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User createuser(User user) {
        userRepository.save(user);
        //tilføj exception logik her
        return user;
    }

    @Override
    public User updateUser(User user) {
        userRepository.findById(user.getUserId()).orElseThrow(() -> new RuntimeException("User not found with id: " + user.getUserId()));
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.findById(user.getUserId()).orElseThrow(() -> new RuntimeException("User not found with id: " + user.getUserId()));
        userRepository.delete(user);
    }

    @Override
    public List<User> readAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User readUsersById(int userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user.getPassword().equals(password)) {
            return user;
        } else if (!user.getPassword().equals(password)){
            throw new ProfileNotFoundException();
        } else{
            return null;
        }



    }



}
