package com.dgf.casumotest.model.calculated;

import com.dgf.casumotest.model.Film;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RentResultLine {
    Film film;
    Double price;
}
