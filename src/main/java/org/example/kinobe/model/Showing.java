package org.example.kinobe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.kinobe.misc.Status;

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
    private Integer showingId;
    private LocalDate date;
    private LocalTime time;
    @Enumerated(EnumType.STRING)
    private Status status;

    ////Added jsonbackrefence since we might run into an infinite loop issue.
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "movieFK")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "theaterFK")
    private Theater theater;
}
