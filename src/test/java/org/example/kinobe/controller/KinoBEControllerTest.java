package org.example.kinobe.controller;

import org.example.kinobe.model.User;
import org.example.kinobe.repository.UserRepository;
import org.example.kinobe.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(UserRestController.class)
public class KinoBEControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;

    @Test
void shouldEditUser() throws Exception{
User testuser = new User(1, "marco", "123hemlighed", "20807993",
        "marco@email.com", LocalDate.of(1999,1,1));

when(userService.updateUser(1, new User())).thenReturn(testuser);

    mockMvc.perform(get("", 1))
            .andExpect(status().isOk())
          //Jeg skal have den side html side jeg bruger
            // .andExpect(view().name("editUserForm"))
            .andExpect(model().attribute("user", testuser));
}


}



