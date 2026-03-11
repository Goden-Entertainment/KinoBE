package org.example.kinobe.h2;

import org.example.kinobe.model.User;
import org.example.kinobe.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class KinoBeRepoTest {


    @Autowired
    private UserRepository userRepository;



//    @Test
//    void createUser(){
//        User user = new User();
//        user.setUsername("Rune");
//        user.setPassword("shuuushh");
//        user.setPhonenumber("20807993");
//        user.setEmail("rune@email.com");
//        user.setDate(LocalDate.of(2000,1,1));
//
//        User userX = new User(1, "Mikkel", "hej", "12345678",
//                "hej@gmail.com",LocalDate.of(2003,9,22));
//
//
//        userRepository.save(user);
//        userRepository.save(userX);
//
//        List<User> users = userRepository.findAll();
//
//        assertThat(users).hasSize(2);
//    }

    @Test
            void delteUser() {

        User user = new User();
        user.setUsername("Rune");
        user.setPassword("shuuushh");
        user.setPhonenumber("20807993");
        user.setEmail("rune@email.com");
        user.setDate(LocalDate.of(2000, 1, 1));

        User userX = new User();
        user.setUsername("Mikkel");
        user.setPassword("129");
        user.setPhonenumber("87654321");
        user.setEmail("mikkel@email.com");
        user.setDate(LocalDate.of(2004, 1, 1));

        userRepository.save(user);
        userRepository.save(userX);

        List<User> usersBefore = userRepository.findAll();

        assertThat(usersBefore).hasSize(3);

        userRepository.deleteById(1);

        List<User> usersAfter = userRepository.findAll();

        assertThat(usersAfter).hasSize(2);

    }


}



