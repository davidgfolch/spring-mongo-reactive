package com.dgf.casumotest.model;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    @Id()
    private String id;

    private String title;
    private FilmRentalType rentalType;
    private int availability;

    public Film(String title, FilmRentalType rentalType, int availability) {
        this.title = title;
        this.rentalType=rentalType;
        this.availability=availability;
    }

    public Optional<Film> availabilityDec() {
        if (availability<=0) {
            return Optional.empty();
        }
        availability=availability-1;
        return Optional.of(this);
    }

    public Film availabilityInc() {
        availability=availability+1;
        return this;
    }
}
