package org.example.kinobe.service;

import org.example.kinobe.model.Movie;
import org.example.kinobe.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie createMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> readAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie readMovieById(int id) {
        return movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));
    }

    @Override
    public Movie updateMovie(Movie movie) {
        movieRepository.findById(movie.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found with id: " + movie.getMovieId()));
        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(Movie movie) {
        movieRepository.findById(movie.getMovieId()).orElseThrow(() -> new RuntimeException("Movie not found with id: " + movie.getMovieId()));
        movieRepository.delete(movie);
    }
}
