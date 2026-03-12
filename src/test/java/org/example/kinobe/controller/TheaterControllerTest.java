package org.example.kinobe.controller;

import org.example.kinobe.model.Movie;
import org.example.kinobe.model.Theater;
import org.example.kinobe.service.TheaterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TheaterController.class)
public class TheaterControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TheaterService theaterService;

    @Autowired
    private ObjectMapper objectMapper;


    private Theater theater1;
    private Theater theater2;

    @BeforeEach
    void theaterSetUp() {
        theater1 = new Theater();
        theater1.setTheaterId(1);
        theater1.setName("sal1");
        theater1.setCapacity(240);

        theater2 = new Theater();
        theater2.setTheaterId(2);
        theater2.setName("sal2");
        theater2.setCapacity(400);
    }

    @Test
    void createTheater() throws Exception {
        when(theaterService.createTheater(any(Theater.class))).thenReturn(theater1);
        mockMvc.perform(post("/theater").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(theater1))).andExpect(status().isCreated())
                .andExpect(jsonPath("$.theaterId").value(1))
                .andExpect(jsonPath("$.name").value("sal1"))
                .andExpect(jsonPath("$.capacity").value(240));

        verify(theaterService, times(1)).createTheater(any(Theater.class));
    }


//    @Test
//    void updateTheaterNotFound() throws Exception {
//        when(theaterService.updateTheater(any(Theater.class))).thenThrow(new RuntimeException("Theater  not found with id: 1"));
//        mockMvc.perform(put("/theater/{theaterId}", 1)
//                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(theater1)))
//                .andExpect(status().isNotFound());
//
//        verify(theaterService, times(1)).updateTheater(any(Theater.class));
//    }

    @Test
    void deleteTheater() throws Exception {
        when(theaterService.readTheaterById(1)).thenReturn(theater1);
        doNothing().when(theaterService).deleteTheater(theater1);

        mockMvc.perform(delete("/theater/{theaterId}", 1)).andExpect(status().isNoContent());

        verify(theaterService, times(1)).readTheaterById(1);
        verify(theaterService, times(1)).deleteTheater(theater1);
    }
}
