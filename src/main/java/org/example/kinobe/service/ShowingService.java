package org.example.kinobe.service;

import org.example.kinobe.model.Showing;

import java.time.LocalDate;
import java.util.List;

public interface ShowingService {

    Showing createShowing(Showing showing);
    Showing updateShowing(Showing showing);
    List<Showing> showingList();
    List<Showing> showingListByMovieId(int movieId);
    List<Showing> showingListByTheaterId(int theaterId);
    Showing getShowingById(int showingId);
    Showing deleteShowingById(int showingId);
    List<Showing> cancelShowingsByMovieId(int movieId, LocalDate removalDate);
}
