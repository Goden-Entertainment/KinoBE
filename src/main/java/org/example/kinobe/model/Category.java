package org.example.kinobe.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int categoryId;
    String genre;

    @ManyToMany(mappedBy = "categories")
    private List<Movie> movies;


    public Category(int categoryId, String genre) {
        this.categoryId = categoryId;
        this.genre = genre;

    }
}
