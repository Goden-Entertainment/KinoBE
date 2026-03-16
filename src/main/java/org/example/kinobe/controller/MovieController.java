package org.example.kinobe.controller;

import org.example.kinobe.model.Movie;
import org.example.kinobe.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        Movie movieCreated = movieService.createMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieCreated);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> readAllMovies() {
        List<Movie> movies = movieService.readAllMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> readMovieById(@PathVariable int id) {
        Movie movie = movieService.readMovieById(id);
        return ResponseEntity.ok(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        movie.setMovieId(id);
        Movie updatedMovie = movieService.updateMovie(movie);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable int id) {
        Movie movie = movieService.readMovieById(id);
        movieService.deleteMovie(movie);
        return ResponseEntity.noContent().build();
    }
}
