package com.dgf.casumotest.model;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFilmsRequest {

    @NotNull
    String userId;
    @NotNull
    List<String> films;

}
