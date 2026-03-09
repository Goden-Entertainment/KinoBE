package org.example.kinobe.service;

import org.example.kinobe.model.Showing;
import org.example.kinobe.repository.ShowingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowingServiceImpl implements ShowingService{
    ShowingRepository repository;

    public ShowingServiceImpl(ShowingRepository repository){
        this.repository = repository;
    }

    @Override
    public Showing createShowing(Showing showing) {
        return repository.save(showing);
    }

    @Override
    public Showing updateShowing(Showing showing) {
        return repository.save(showing);
    }

    @Override
    public List<Showing> showingList() {
        return repository.findAll();
    }

    @Override
    public List<Showing> showingListByMovieId(int movieId){
        return repository.findAllByMovieId(movieId);
    }

    @Override
    public List<Showing> showingListByTheaterId(int theaterId){
        return repository.findAllByTheaterId(theaterId);
    }

    @Override
    public Showing getShowingById(int showingId) {
        return repository.findById(showingId).orElseThrow(() ->
                new RuntimeException("Showing with id " + showingId + " was not found"));
    }

    @Override
    public Showing deleteShowingById(int showingId) {
        Showing showing = repository.findById(showingId).orElseThrow(() ->
                new RuntimeException("Showing with id " + showingId + " was not found"));
        repository.deleteById(showingId);
        return showing;
    }
}
