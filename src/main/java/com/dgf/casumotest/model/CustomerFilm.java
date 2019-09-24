package com.dgf.casumotest.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerFilm {

    final String id;
    Date date;
    FilmRentalType rentalType;

}
