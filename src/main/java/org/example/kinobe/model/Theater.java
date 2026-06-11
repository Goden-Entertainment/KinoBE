package org.example.kinobe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer theaterId;
    private String name;
    private Integer capacity;

    public Theater(int theaterId, int capacity, String name) {
        this.theaterId = theaterId;
        this.capacity = capacity;
        this.name = name;
    }
}