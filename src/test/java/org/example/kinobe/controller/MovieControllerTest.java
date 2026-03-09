package org.example.kinobe.controller;

import org.example.kinobe.model.Movie;
import org.example.kinobe.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    private Movie movie1;
    private Movie movie2;

    @BeforeEach
    void setUp() {
        movie1 = new Movie();
        movie1.setMovieId(1);
        movie1.setTitle("movieTitle1");
        movie1.setAgeLimit(13);
        movie1.setDuration(120);
        movie1.setDescription("Movie 1");

        movie2 = new Movie();
        movie2.setMovieId(2);
        movie2.setTitle("MovieTitle2");
        movie2.setAgeLimit(13);
        movie2.setDuration(152);
        movie2.setDescription("Movie 2");
    }

    @Test
    void createMovie() throws Exception {
        when(movieService.createMovie(any(Movie.class))).thenReturn(movie1);
        mockMvc.perform(post("/movie").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movie1))).andExpect(status().isCreated())
                .andExpect(jsonPath("$.movieId").value(1))
                .andExpect(jsonPath("$.title").value("movieTitle1"))
                .andExpect(jsonPath("$.description").value("Movie 1"));

        verify(movieService, times(1)).createMovie(any(Movie.class));
    }

    @Test
    void readAllMovies() throws Exception {
        when(movieService.readAllMovies()).thenReturn(List.of(movie1, movie2));
        mockMvc.perform(get("/movie"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("movieTitle1"))
                .andExpect(jsonPath("$[1].title").value("MovieTitle2"));

        verify(movieService, times(1)).readAllMovies();
    }

    @Test
    void readMovieById() throws Exception {
        when(movieService.readMovieById(1)).thenReturn(movie1);
        mockMvc.perform(get("/movie/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.movieId").value(1))
                .andExpect(jsonPath("$.title").value("movieTitle1"))
                .andExpect(jsonPath("$.description").value("Movie 1"));

        verify(movieService, times(1)).readMovieById(1);
    }

    @Test
    void readMovieByIdNotFound() throws Exception {
        when(movieService.readMovieById(3))
                .thenThrow(new RuntimeException("Movie not found with id: 3"));

        mockMvc.perform(get("/movie/{id}", 3))
                .andExpect(status().isNotFound());

        verify(movieService, times(1)).readMovieById(3);
    }

    @Test
    void updateMovie() throws Exception {
        movie1.setTitle("movieTitle Updated");
        movie1.setDescription("An updated description");
        when(movieService.updateMovie(any(Movie.class))).thenReturn(movie1);
        mockMvc.perform(put("/movie/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(movie1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("movieTitle Updated"))
                .andExpect(jsonPath("$.description").value("An updated description"));

        verify(movieService, times(1)).updateMovie(any(Movie.class));
    }

    @Test
    void updateMovieNotFound() throws Exception {
        when(movieService.updateMovie(any(Movie.class))).thenThrow(new RuntimeException("Movie not found with id: 3"));
        mockMvc.perform(put("/movie/{id}", 3)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(movie1)))
                .andExpect(status().isNotFound());

        verify(movieService, times(1)).updateMovie(any(Movie.class));
    }

    @Test
    void deleteMovie() throws Exception {
        when(movieService.readMovieById(1)).thenReturn(movie1);
        doNothing().when(movieService).deleteMovie(movie1);

        mockMvc.perform(delete("/movie/{id}", 1)).andExpect(status().isNoContent());

        verify(movieService, times(1)).readMovieById(1);
        verify(movieService, times(1)).deleteMovie(movie1);
    }

    @Test
    void deleteMovieNotFound() throws Exception {
        when(movieService.readMovieById(3)).thenThrow(new RuntimeException("Movie not found with id: 3"));

        mockMvc.perform(delete("/movie/{id}", 3)).andExpect(status().isNotFound());

        verify(movieService, never()).deleteMovie(any(Movie.class));
    }
}