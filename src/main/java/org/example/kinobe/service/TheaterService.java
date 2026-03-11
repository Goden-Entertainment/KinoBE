package org.example.kinobe.service;


import org.example.kinobe.model.Theater;

import java.util.List;

public interface TheaterService {
    Theater createTheater(Theater theater);
    List<Theater> readAllTheater();
    Theater readTheaterById(int id);
    Theater updateTheater(Theater theater);
    void deleteTheater(Theater theater);
}
