package com.dgf.casumotest.model.calculated;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RentResult {
    List<RentResultLine> films;
    double total;
}
