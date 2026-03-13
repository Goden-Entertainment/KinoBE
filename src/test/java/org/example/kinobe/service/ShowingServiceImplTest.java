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
        Theater theater = new Theater(1, 100, "Test Theater");
        Showing showing = Showing.builder().date(LocalDate.now()).time(LocalTime.now()).status(Status.ACTIVE).movie(movie).theater(theater).build();

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
}