package org.example.kinobe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.kinobe.exception.InvalidShowingDataException;
import org.example.kinobe.misc.Status;
import org.example.kinobe.misc.TimeRange;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Showing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int showingId;
    private LocalDate date;
    private LocalTime time;
    @Enumerated(EnumType.STRING)
    private Status status;

    ////Added jsonbackrefence since we might run into an infinite loop issue.
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "movieFK", referencedColumnName = "movieId")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "theaterFK", referencedColumnName = "theaterId")
    private Theater theater;

    public TimeRange getShowingTimeRange(int bufferMinutes){
        if(movie == null){
            throw new InvalidShowingDataException("This Showing: " + this + ", has a movie object that is null.");
        }else if(time == null){
            throw new InvalidShowingDataException("This Showing: " + this + ", has a time variable that is null.");
        }

        int movieDuration = movie.getDuration();
        LocalTime start = time;
        LocalTime end = time.plusMinutes(movieDuration + bufferMinutes);
        return new TimeRange(start, end);
    }
}
