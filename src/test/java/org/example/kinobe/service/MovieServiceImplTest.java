package org.example.kinobe.service;

import org.example.kinobe.model.Movie;
import org.example.kinobe.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setMovieId(1);
        movie.setTitle("Movie1 Title");
        movie.setAgeLimit(13);
        movie.setDuration(250);
        movie.setDescription("Movie1 Description");
    }

    @Test
    void createMovie() {
        when(movieRepository.save(movie)).thenReturn(movie);
        Movie result = movieService.createMovie(movie);
        assertNotNull(result);
        assertEquals("Movie1 Title", result.getTitle());
        assertEquals(1, result.getMovieId());
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    void readAllMovies() {
        Movie movie2 = new Movie();
        movie2.setMovieId(2);
        movie2.setTitle("Movie2 Title");
        when(movieRepository.findAll()).thenReturn(Arrays.asList(movie, movie2));
        List<Movie> result = movieService.readAllMovies();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Movie1 Title", result.get(0).getTitle());
        assertEquals("Movie2 Title", result.get(1).getTitle());
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    void readAllMoviesReturnNull() {
        when(movieRepository.findAll()).thenReturn(List.of());
        List<Movie> result = movieService.readAllMovies();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    void readMovieById() {
        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        Movie result = movieService.readMovieById(1);

        assertNotNull(result);
        assertEquals(1, result.getMovieId());
        assertEquals("Movie1 Title", result.getTitle());
        verify(movieRepository, times(1)).findById(1);
    }

    @Test
    void readMovieByIdNotFound() {
        when(movieRepository.findById(3)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> movieService.readMovieById(3));

        assertEquals("Movie not found with id: 3", exception.getMessage());
        verify(movieRepository, times(1)).findById(3);
    }

    @Test
    void updateMovie() {
        movie.setTitle("Updated Title");
        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        when(movieRepository.save(movie)).thenReturn(movie);
        Movie result = movieService.updateMovie(movie);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        verify(movieRepository, times(1)).findById(1);
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    void updateMovieNotFound() {
        movie.setMovieId(3);
        when(movieRepository.findById(3)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> movieService.updateMovie(movie));
        verify(movieRepository, never()).save(any(Movie.class));
    }

    @Test
    void deleteMovie() {
        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        movieService.deleteMovie(movie);
        verify(movieRepository, times(1)).delete(movie);
    }

    @Test
    void deleteMovieNotFound() {
        movie.setMovieId(3);
        when(movieRepository.findById(3)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> movieService.deleteMovie(movie));
        verify(movieRepository, never()).delete(any(Movie.class));
    }
}