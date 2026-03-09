package org.example.kinobe.service;

import org.example.kinobe.model.Movie;
import org.example.kinobe.model.Theater;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterServiceImpl implements TheaterService {

    @Override
    public Theater createTheater(Theater theater) {
        return null;
    }

    @Override
    public List<Movie> readAllTheater() {
        return List.of();
    }

    @Override
    public Theater readTheaterById(int id) {
        return null;
    }

    @Override
    public Theater updateTheater(Theater theater) {
        return null;
    }

    @Override
    public void deleteTheater(Theater theater) {

    }
}
