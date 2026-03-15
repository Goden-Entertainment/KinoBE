package org.example.kinobe.service;

import org.example.kinobe.misc.Status;
import org.example.kinobe.model.Movie;
import org.example.kinobe.model.Showing;
import org.example.kinobe.model.Theater;
import org.example.kinobe.repository.ShowingRepository;
import org.example.kinobe.exception.InvalidShowingDataException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShowingServiceImplTest {
    @Mock
    ShowingRepository repository;

    @InjectMocks
    ShowingServiceImpl service;


    @Test
    void showingServiceImpl_CreateShowingWithAcceptableDate_DoesNotThrowInvalidShowingDataException(){
        Movie movie = new Movie();
        movie.setDuration(120);
        movie.setStatus(Status.ACTIVE);
        Theater theater = new Theater(1, 100, "Test Theater");
        Showing showing = Showing.builder().date(LocalDate.now()).time(LocalTime.of(10, 0)).status(Status.ACTIVE).movie(movie).theater(theater).build();

        assertDoesNotThrow(() -> service.createShowing(showing));
        verify(repository).save(showing);
    }


    @Test
    void showingServiceImpl_CreateShowingWithDateBeforeToday_ThrowsInvalidShowingDataException(){
        Showing showing = Showing.builder().date(LocalDate.now().minusDays(1)).time(LocalTime.now()).status(Status.ACTIVE).build();

        assertThrows(InvalidShowingDataException.class, () -> service.createShowing(showing));
    }


    @Test
    void showingServiceImpl_CreateShowingWithDateFourMonthsFromToday_ThrowsRuntimeException(){
        Showing showing = Showing.builder().date(LocalDate.now().plusMonths(4)).time(LocalTime.now()).status(Status.ACTIVE).build();

        assertThrows(InvalidShowingDataException.class, () -> service.createShowing(showing));
    }


    @Test
    void showingServiceImpl_DeleteShowingById_ReturnsShowing() {
        Showing showing = Showing.builder().showingId(1).date(LocalDate.now()).time(LocalTime.now()).status(Status.ACTIVE).build();

        when(repository.findById(1)).thenReturn(Optional.of(showing));

        Showing result = service.deleteShowingById(1);

        assertThat(result).isEqualTo(showing);
        verify(repository).deleteById(1);
    }

    // --- checkShowingMovie ---

    @Test
    void checkShowingMovie_NullShowing_ThrowsInvalidShowingDataException() {
        assertThrows(InvalidShowingDataException.class, () -> service.checkShowingMovie(null));
    }

    @Test
    void checkShowingMovie_NullMovie_ThrowsInvalidShowingDataException() {
        Showing showing = Showing.builder().movie(null).build();
        assertThrows(InvalidShowingDataException.class, () -> service.checkShowingMovie(showing));
    }

    @Test
    void checkShowingMovie_NullMovieStatus_ThrowsInvalidShowingDataException() {
        Movie movie = new Movie();
        movie.setStatus(null);
        Showing showing = Showing.builder().movie(movie).build();
        assertThrows(InvalidShowingDataException.class, () -> service.checkShowingMovie(showing));
    }

    @Test
    void checkShowingMovie_CancelledMovieStatus_ThrowsInvalidShowingDataException() {
        Movie movie = new Movie();
        movie.setStatus(Status.CANCELLED);
        Showing showing = Showing.builder().movie(movie).build();
        assertThrows(InvalidShowingDataException.class, () -> service.checkShowingMovie(showing));
    }

    @Test
    void checkShowingMovie_ActiveMovie_DoesNotThrow() {
        Movie movie = new Movie();
        movie.setStatus(Status.ACTIVE);
        Showing showing = Showing.builder().movie(movie).build();
        assertDoesNotThrow(() -> service.checkShowingMovie(showing));
    }

    // --- checkDailyTimeLimitations ---

    @Test
    void checkDailyTimeLimitations_NullShowing_ThrowsInvalidShowingDataException() {
        assertThrows(InvalidShowingDataException.class, () -> service.checkDailyTimeLimitations(null));
    }

    @Test
    void checkDailyTimeLimitations_NonExtraShowing_DoesNotThrow() {
        Movie movie = new Movie();
        movie.setDuration(120);
        // ACTIVE showing outside cinema hours — should be skipped entirely
        Showing showing = Showing.builder()
                .status(Status.ACTIVE)
                .time(LocalTime.of(1, 0))
                .movie(movie)
                .build();
        assertDoesNotThrow(() -> service.checkDailyTimeLimitations(showing));
    }

    @Test
    void checkDailyTimeLimitations_ExtraShowingWithinHours_DoesNotThrow() {
        Movie movie = new Movie();
        movie.setDuration(60);
        // starts 10:00, ends 11:15 (60 min + 15 buffer) — within 10:00–23:00
        Showing showing = Showing.builder()
                .status(Status.EXTRASHOWING)
                .time(LocalTime.of(10, 0))
                .movie(movie)
                .build();
        assertDoesNotThrow(() -> service.checkDailyTimeLimitations(showing));
    }

    @Test
    void checkDailyTimeLimitations_ExtraShowingStartingBeforeOpening_ThrowsInvalidShowingDataException() {
        Movie movie = new Movie();
        movie.setDuration(60);
        // starts 09:00, ends 10:15 — start is before opening (10:00)
        Showing showing = Showing.builder()
                .status(Status.EXTRASHOWING)
                .time(LocalTime.of(9, 0))
                .movie(movie)
                .build();
        assertThrows(InvalidShowingDataException.class, () -> service.checkDailyTimeLimitations(showing));
    }

    @Test
    void checkDailyTimeLimitations_ExtraShowingEndingAfterClosing_ThrowsInvalidShowingDataException() {
        Movie movie = new Movie();
        movie.setDuration(120);
        // starts 21:00, ends 23:15 (120 min + 15 buffer) — end exceeds 23:00
        Showing showing = Showing.builder()
                .status(Status.EXTRASHOWING)
                .time(LocalTime.of(21, 0))
                .movie(movie)
                .build();
        assertThrows(InvalidShowingDataException.class, () -> service.checkDailyTimeLimitations(showing));
    }
}