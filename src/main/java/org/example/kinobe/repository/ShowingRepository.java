package org.example.kinobe.repository;

import org.example.kinobe.model.Showing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowingRepository extends JpaRepository<Showing, Integer> {

    List<Showing> findAllByMovie_MovieId(int movieId);
    List<Showing> findAllByTheater_TheaterId(int theaterId);

}