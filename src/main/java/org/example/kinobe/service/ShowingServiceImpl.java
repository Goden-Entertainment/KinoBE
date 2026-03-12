package org.example.kinobe.service;

import org.example.kinobe.exception.InvalidShowingDataException;
import org.example.kinobe.misc.Status;
import org.example.kinobe.model.Showing;
import org.example.kinobe.repository.ShowingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.TemporalField;
import java.util.List;

@Service
public class ShowingServiceImpl implements ShowingService{
    ShowingRepository repository;
    LocalDate today = LocalDate.now();
    LocalDate maxDate = today.plusMonths(3);
    int dateGapMonth = Period.between(today, maxDate).getMonths();

    public final String dateBeforeTodayMsg = "Invalid date, Showing cannot be scheduled earlier than the current day.";
    public final String dateNotWithInThreeMonthsMsg = "Invalid date, Showing must be scheduled within " + dateGapMonth + " months of the current month.";

    public ShowingServiceImpl(ShowingRepository repository){
        this.repository = repository;
    }

    @Override
    public Showing createShowing(Showing showing) {
        if(showing == null){
            throw new InvalidShowingDataException("Showing is NUll");
        }

        if(showing.getDate().isBefore(today)){
            throw new InvalidShowingDataException(dateBeforeTodayMsg);
        }else if(showing.getDate().isAfter(maxDate)){
            throw new InvalidShowingDataException(dateNotWithInThreeMonthsMsg);
        }
        return repository.save(showing);
    }

    @Override
    public Showing updateShowing(Showing showing) {
        if(showing == null){
            throw new InvalidShowingDataException("Showing is NUll");
        }

        if(showing.getDate().isBefore(today)){
            throw new InvalidShowingDataException(dateBeforeTodayMsg);
        }else if(showing.getDate().isAfter(maxDate)){
            throw new InvalidShowingDataException(dateNotWithInThreeMonthsMsg);
        }
        return repository.save(showing);
    }

    @Override
    public List<Showing> showingList() {
        return repository.findAll();
    }

    @Override
    public List<Showing> showingListByMovieId(int movieId){
        return repository.findAllByMovie_MovieId(movieId);
    }

    @Override
    public List<Showing> showingListByTheaterId(int theaterId){
        return repository.findAllByTheater_TheaterId(theaterId);
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

    @Override
    public List<Showing> cancelShowingsByMovieId(int movieId, LocalDate removalDate){
        List<Showing> showings = repository.findAllByMovie_MovieIdAndDateAfter(movieId, removalDate);

        for(Showing showing : showings){
            showing.setStatus(Status.CANCELLED);
            repository.save(showing);
        }
        return showings;

    }
}
