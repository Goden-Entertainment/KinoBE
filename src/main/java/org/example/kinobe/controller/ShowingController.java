package org.example.kinobe.controller;

import org.example.kinobe.exception.InvalidShowingDataException;
import org.example.kinobe.model.Showing;
import org.example.kinobe.service.ShowingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/showing")
@RestController
@CrossOrigin("*")
public class ShowingController {
    ShowingService service;

    public ShowingController(ShowingService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createShowing(@RequestBody Showing showing){
        try{
            Showing saved = service.createShowing(showing);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }catch(InvalidShowingDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Showing>> showingList(){
        List<Showing> showingList = service.showingList();
        return new ResponseEntity<>(showingList, HttpStatus.OK);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<Showing>> showingListByMovieId(@PathVariable int movieId){
        List<Showing> showingList = service.showingListByMovieId(movieId);
        return new ResponseEntity<>(showingList, HttpStatus.OK);
    }

    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<Showing>> showingListByTheaterFK(@PathVariable int theaterId){
        List<Showing> showingList = service.showingListByTheaterId(theaterId);
        return new ResponseEntity<>(showingList, HttpStatus.OK);
    }

    @GetMapping("/{showingId}")
    public ResponseEntity<Showing> getShowingById(@PathVariable int showingId){
        Showing showing = service.getShowingById(showingId);
        return new ResponseEntity<>(showing, HttpStatus.OK);
    }

    @PutMapping("/{showingId}")
    public ResponseEntity<?> updateShowingById(@PathVariable int showingId, @RequestBody Showing showing){
        try{
            showing.setShowingId(showingId);
            Showing saved = service.updateShowing(showing);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        }catch(InvalidShowingDataException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{showingId}")
    public ResponseEntity<Showing> deleteShowingById(@PathVariable int showingId){
        Showing deleted = service.deleteShowingById(showingId);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    //Puts all showings of that selected movie to CANCELLED status.
    @PutMapping("/cancel/movie/{movieId}")
    public ResponseEntity<List<Showing>> cancelShowingsByMovieId(@PathVariable int movieId, @RequestParam LocalDate removalDate){
        List<Showing> cancelled = service.cancelShowingsByMovieId(movieId, removalDate);
        return new ResponseEntity<>(cancelled, HttpStatus.OK);
    }
}
