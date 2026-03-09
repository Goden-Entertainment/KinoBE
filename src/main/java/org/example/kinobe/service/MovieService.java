package org.example.kinobe.service;

import org.example.kinobe.model.Movie;

import java.util.List;

public interface MovieService {
    Movie createMovie(Movie movie);
    List<Movie> readAllMovies();
    Movie readMovieById(int id);
    Movie updateMovie(Movie movie);
    void deleteMovie(Movie movie);
}
