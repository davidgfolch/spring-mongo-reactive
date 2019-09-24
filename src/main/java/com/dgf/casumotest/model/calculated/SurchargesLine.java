package com.dgf.casumotest.model.calculated;

import com.dgf.casumotest.model.CustomerFilm;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SurchargesLine {

    CustomerFilm film;
    Double surcharge;

}
