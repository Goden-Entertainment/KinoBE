package org.example.kinobe.service;

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
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(int userId, User user) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User ikke fundet"));

        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setEmail(user.getEmail());
        existingUser.setDate(user.getDate());
        existingUser.setPhonenumber(user.getPhonenumber());


        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }


}
