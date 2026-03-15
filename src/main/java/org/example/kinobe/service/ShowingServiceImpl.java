package org.example.kinobe.service;

import org.example.kinobe.exception.InvalidShowingDataException;
import org.example.kinobe.misc.Status;
import org.example.kinobe.misc.TimeRange;
import org.example.kinobe.model.Movie;
import org.example.kinobe.model.Showing;
import org.example.kinobe.repository.ShowingRepository;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

@Service
public class ShowingServiceImpl implements ShowingService{
    ShowingRepository repository;

    static final int bufferMinutes = 15;

    public ShowingServiceImpl(ShowingRepository repository){
        this.repository = repository;
    }

    @Override
    public Showing createShowing(Showing showing) {
        if(showing == null){
            throw new InvalidShowingDataException("Showing is NUll");
        }

        checkShowingMovie(showing);
        checkForDateConflict(showing);
        checkForTimeConflict(showing);
        return repository.save(showing);
    }

    @Override
    public void checkShowingMovie(Showing showing){
        if(showing == null){
            throw new InvalidShowingDataException("ShowingServiceImpl, method: checkShowingMovie, showing is null.");
        }
        if(showing.getMovie() == null){
            throw new InvalidShowingDataException("This Showing: " + showing + ", must contain a movie object.");
        }
        Movie movie = showing.getMovie();

        if(movie.getStatus() == null){
            throw new InvalidShowingDataException("This Showing: " + showing + ", contains this Movie: " + movie + ", a Movie must contain a status");
        }else if(movie.getStatus().equals(Status.CANCELLED)){
            throw new InvalidShowingDataException("This Showing: " + showing + ", contains this Movie: " + movie + ", a Movie must not have the status CANCELLED");
        }
    }

    @Override
    public void checkForDateConflict(Showing showing){
        if(showing.getDate() == null){
            throw new InvalidShowingDataException("Showing must have a date");
        }
        LocalDate today = LocalDate.now();
        LocalDate maxDate = today.plusMonths(3);
        int dateGapMonth = Period.between(today, maxDate).getMonths();

        String dateBeforeTodayMsg = "Invalid date, Showing cannot be scheduled earlier than the current day.";
        String dateNotWithInThreeMonthsMsg = "Invalid date, Showing must be scheduled within " + dateGapMonth + " months of the current month.";

        if(showing.getDate().isBefore(today)){
            throw new InvalidShowingDataException(dateBeforeTodayMsg);
        }else if(showing.getDate().isAfter(maxDate)){
            throw new InvalidShowingDataException(dateNotWithInThreeMonthsMsg);
        }
    }

    @Override
    public void checkForTimeConflict(Showing showing){
        if(showing.getMovie() == null){
            throw new InvalidShowingDataException("Showing must have a movie assigned.");
        }
        if(showing.getTheater() == null){
            throw new InvalidShowingDataException("Showing must have a theater assigned.");
        }

        TimeRange showingTimeSlot = showing.getShowingTimeRange(bufferMinutes);

        int theaterId = showing.getTheater().getTheaterId();
        List<Showing> existingShowings = repository.findAllByTheater_TheaterIdAndDateAndStatusNot(
                theaterId,
                showing.getDate(),
                Status.CANCELLED
        );

        for(Showing s : existingShowings){
            if(s.getShowingId() != null && s.getShowingId().equals(showing.getShowingId())){
                continue;
            }

            TimeRange existShowingTimeSlot = s.getShowingTimeRange(bufferMinutes);

            if(showingTimeSlot.overlaps(existShowingTimeSlot)){
                throw new InvalidShowingDataException("This: " + showingTimeSlot + " overlaps with existing timeslot: " + existShowingTimeSlot);
            }
        }
    }

    @Override
    public void checkDailyTimeLimitations(Showing showing){
        if(showing == null){
            throw new InvalidShowingDataException("ShowingServiceImpl, method: checkForExtraShowing, showing is null.");
        }else if(!(showing.getStatus().equals(Status.EXTRASHOWING))){
            return;
        }

        //Opening-time 10:00
        LocalTime cinemaOpeningTime = LocalTime.of(10,0);
        //Closing-time
        LocalTime cinemaClosingTime = LocalTime.of(23,0);
        //Time from opening to closing
        TimeRange cinemaDailyScheduleLength = new TimeRange(cinemaOpeningTime, cinemaClosingTime);

        //Showing time-slot variable
        TimeRange showingTimeSlot = showing.getShowingTimeRange(bufferMinutes);

        if(!showingTimeSlot.isWithin(cinemaDailyScheduleLength)){
            throw new InvalidShowingDataException("ShowingServiceImpl, method: checkDailyTimeLimitations, showing is not within the daily schedule limits.");
        }
    }

    @Override
    public Showing updateShowing(Showing showing) {
        if(showing == null){
            throw new InvalidShowingDataException("Showing is NUll");
        }

        //Checks if Showing has a Movie Object
        // and if the Movie has the status CANCELLED.
        checkShowingMovie(showing);
        //Checks if the Showing is being scheduled more than 3 months from now
        // or earlier than today.
        checkForDateConflict(showing);
        //Checks if Showing overlaps with other showings
        // on the same schedule in the same theater
        checkForTimeConflict(showing);
        //Checks that showing is not scheduled before the cinema opens
        // or after the cinema closes, unless the showing has the status of EXTRA.
        checkDailyTimeLimitations(showing);
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
