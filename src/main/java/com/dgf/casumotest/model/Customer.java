package com.dgf.casumotest.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class Customer {

    @Id()
    private String id;
    private String firstName;
    private String lastName;
    private List<CustomerFilm> films = new ArrayList<>();
    private int points=0;

    public Customer(String id, String firstName, String lastName) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.id=id;
    }

}
