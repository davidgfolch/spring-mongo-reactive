package com.dgf.casumotest.model.calculated;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Surcharges {

    List<SurchargesLine> films;
    Double total;

}
