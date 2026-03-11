package org.example.kinobe.model;

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

//    @ManyToOne
//    @JoinColumn(name = "movieFK", referencedColumnName = "movieId")
//    @Column(name = "movieFK")
//    private Movie movie;

//    @ManyToOne
//    @JoinColumn(name = "theaterFK", referencedColumnName = "theatherId")
//    @Column(name = "teatherFK")
//    private Theater theater;


}
