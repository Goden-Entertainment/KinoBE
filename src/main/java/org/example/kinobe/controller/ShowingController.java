package org.example.kinobe.controller;

import org.example.kinobe.model.Showing;
import org.example.kinobe.service.ShowingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/showing")
@RestController
public class ShowingController {
    ShowingService service;

    public ShowingController(ShowingService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Showing> createShowing(@RequestBody Showing showing){
        Showing saved = service.createShowing(showing);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
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
    public ResponseEntity<Showing> updateShowingById(@PathVariable int showingId, @RequestBody Showing showing){
        showing.setShowingId(showingId);
        Showing saved = service.updateShowing(showing);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @DeleteMapping("{showingId}")
    public ResponseEntity<Showing> deleteShowingById(@PathVariable int showingId){
        Showing deleted = service.deleteShowingById(showingId);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }
}
