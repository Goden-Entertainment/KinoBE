package org.example.kinobe.model;

import jakarta.persistence.*;
import org.example.kinobe.misc.Status;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;
    private String title;
    private int ageLimit;
    private int duration;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String image;

    /*TODO: uncomment this when Showing and Category class is created.
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Showing> showings;

    @ManyToMany
    @JoinTable(
            name = "Category_Movie",
            joinColumns = @JoinColumn(name = "movieFK"),
            inverseJoinColumns = @JoinColumn(name = "categoryFK")
    )
    private List<Category> categories;
    */

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
