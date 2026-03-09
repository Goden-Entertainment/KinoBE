package org.example.kinobe.service;

import org.example.kinobe.model.Showing;
import org.example.kinobe.repository.ShowingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShowingServiceImplTest {
    @Mock
    ShowingRepository repository;

    @InjectMocks
    ShowingServiceImpl service;

    @Test
    void showingServiceImpl_DeleteShowingById_ReturnsShowing() {
        Showing showing = new Showing(1, LocalDate.now(), LocalTime.now(), "ACTIVE");

        when(repository.findById(1)).thenReturn(Optional.of(showing));

        Showing result = service.deleteShowingById(1);

        assertThat(result).isEqualTo(showing);
        verify(repository).deleteById(1);
    }
}