package org.example.kinobe.service;

import org.example.kinobe.model.Theater;
import org.example.kinobe.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterServiceImpl implements TheaterService {

    private final TheaterRepository theaterRepository;

    public TheaterServiceImpl(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    @Override
    public Theater createTheater(Theater theater) {
        return theaterRepository.save(theater);
    }

    @Override
    public List<Theater> readAllTheater() {
        return theaterRepository.findAll();
    }

    @Override
    public Theater readTheaterById(int id) {
        return theaterRepository.findById(id).orElseThrow(() -> new RuntimeException("Theater not found with id: " + id));
    }

    @Override
    public Theater updateTheater(Theater theater) {
        return theaterRepository.findById(theater.getTheaterId()).orElseThrow(() -> new RuntimeException("Theater not found with id: " + theater.getTheaterId()));
    }

    @Override
    public void deleteTheater(Theater theater) {
        theaterRepository.findById(theater.getTheaterId()).orElseThrow(() -> new RuntimeException("Theater not found with id: " + theater.getTheaterId()));
        theaterRepository.delete(theater);
    }
}
