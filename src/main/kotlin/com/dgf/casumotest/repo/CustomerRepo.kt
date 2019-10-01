package com.dgf.casumotest.repo;

import com.dgf.casumotest.model.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;


public interface CustomerRepo extends ReactiveMongoRepository<Customer, String> {

    Flux<Customer> findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);

}
