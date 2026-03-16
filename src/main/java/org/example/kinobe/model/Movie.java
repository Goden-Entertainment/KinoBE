package org.example.kinobe.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.kinobe.misc.Status;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;
    private String title;
    private Integer ageLimit;
    private Integer duration;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;
    private String image;

    //Added jsonmanagedrefence since we might run into an infinite loop issue.
    @JsonManagedReference
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Showing> showings;

    @ManyToMany
    @JoinTable(
            name = "Category_Movie",
            joinColumns = @JoinColumn(name = "movieFK"),
            inverseJoinColumns = @JoinColumn(name = "categoryFK")
    )
    private List<Category> categories;
}
