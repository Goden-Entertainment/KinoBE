package org.example.kinobe.service;

import org.example.kinobe.model.Showing;
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
        Showing showing = new Showing(1, LocalDate.now(), LocalTime.now(), "ACTIVE");

        assertDoesNotThrow(() -> service.createShowing(showing));
        verify(repository).save(showing);
    }

    @Test
    void showingServiceImpl_CreateShowingWithDateBeforeToday_ThrowsInvalidShowingDataException(){
        Showing showing = new Showing(1, LocalDate.now().minusDays(1), LocalTime.now(), "ACTIVE");

        RuntimeException e = assertThrows(InvalidShowingDataException.class, () -> service.createShowing(showing));
        assertEquals("Invalid date, Showing cannot be scheduled earlier than the current day.", e.getMessage());
    }

    @Test
    void showingServiceImpl_CreateShowingWithDateFourMonthsFromToday_ThrowsRuntimeException(){
        Showing showing = new Showing(1, LocalDate.now().plusMonths(4), LocalTime.now(), "ACTIVE");

        RuntimeException e = assertThrows(InvalidShowingDataException.class, () -> service.createShowing(showing));
        assertEquals("Invalid date, Showing must be scheduled within 3 months of the current month.", e.getMessage());
    }

    @Test
    void showingServiceImpl_DeleteShowingById_ReturnsShowing() {
        Showing showing = new Showing(1, LocalDate.now(), LocalTime.now(), "ACTIVE");

        when(repository.findById(1)).thenReturn(Optional.of(showing));

        Showing result = service.deleteShowingById(1);

        assertThat(result).isEqualTo(showing);
        verify(repository).deleteById(1);
    }
}