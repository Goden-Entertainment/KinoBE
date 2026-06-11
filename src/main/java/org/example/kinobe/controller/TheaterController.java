package org.example.kinobe.controller;

import org.example.kinobe.exception.ResourceNotFoundException;
import org.example.kinobe.model.Theater;
import org.example.kinobe.service.TheaterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theater")
public class TheaterController {

    private final TheaterService theaterService;


    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @PostMapping
    public ResponseEntity<Theater> createTheater(@RequestBody Theater theater){
        Theater theaterCreated =  theaterService.createTheater(theater);
        return ResponseEntity.status(HttpStatus.CREATED).body(theaterCreated);
    }

    @GetMapping
    public ResponseEntity<List<Theater>> readAllTheater(){
        List<Theater> theaters = theaterService.readAllTheater();
        return ResponseEntity.ok(theaters);
    }

    @GetMapping("/{theaterId}")
    public ResponseEntity<Theater> readTheaterById(@PathVariable int theaterId){
        Theater theater = theaterService.readTheaterById(theaterId);
        if(theater == null){
            throw new ResourceNotFoundException("Theater not found with id: " + theaterId);
        }
        return ResponseEntity.ok(theater);

    }

    @PutMapping("/{theaterId}")
    public ResponseEntity<Theater> updateTheater(@PathVariable int theaterId, @RequestBody Theater theater){
        theater.setTheaterId(theaterId);
        Theater updateTheater = theaterService.updateTheater(theater);
        return ResponseEntity.ok(updateTheater);
    }

    @DeleteMapping("/{theaterId}")
    public ResponseEntity<Void> deleteTheater(@PathVariable int theaterId){
        Theater theater = theaterService.readTheaterById(theaterId);
        theaterService.deleteTheater(theater);
        return ResponseEntity.noContent().build();
    }

}
