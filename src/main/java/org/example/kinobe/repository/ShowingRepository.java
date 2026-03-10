package org.example.kinobe.repository;

import org.example.kinobe.model.Showing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowingRepository extends JpaRepository<Showing, Integer> {

    List<Showing> findAllByMovieId(int movieId);
    List<Showing> findAllByTheaterId(int theaterId);

}