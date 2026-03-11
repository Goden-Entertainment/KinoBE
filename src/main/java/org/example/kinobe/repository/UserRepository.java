package org.example.kinobe.repository;

import org.example.kinobe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findUser(String username);
    User readUserByUserId(int userId);
}
