package org.example.kinobe.service;

import org.example.kinobe.exception.InvalidShowingDataException;
import org.example.kinobe.misc.Status;
import org.example.kinobe.model.Showing;
import org.example.kinobe.model.Theater;
import org.example.kinobe.repository.ShowingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    static final int bufferMinutes = 15;

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
    public void checkForTimeConflict(Showing showing){
        if(showing.getMovie() == null){
            throw new InvalidShowingDataException("Showing must have a movie assigned.");
        }
        if(showing.getTheater() == null){
            throw new InvalidShowingDataException("Showing must have a theater assigned.");
        }

        int movieDuration = showing.getMovie().getDuration();
        LocalTime start = showing.getTime();
        LocalTime end = start.plusMinutes(movieDuration + bufferMinutes);

        int theaterId = showing.getTheater().getTheaterId();
        List<Showing> existing = repository.findAllByTheater_TheaterIdDateAndStatusNotCancelled(
                theaterId,
                showing.getDate(),
                Status.CANCELLED
        );

        for(Showing s : existing){
            if(showing.getShowingId() != null && showing.getShowingId().equals(s.getShowingId())){
                continue;
            }
            int sMovieDuration = s.getMovie().getDuration();
            LocalTime existStart = s.getTime();
            LocalTime existEnd = existStart.plusMinutes(sMovieDuration + bufferMinutes);

            if(start.isBefore(existStart) && end.isAfter(existStart)){
                throw new InvalidShowingDataException(
                        "Theater is already booked from " + existStart + " to " + existEnd + "."
                );
            }
        }
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
