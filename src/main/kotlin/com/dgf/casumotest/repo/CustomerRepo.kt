package com.dgf.casumotest.repo

import com.dgf.casumotest.model.Customer
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux


interface CustomerRepo : ReactiveMongoRepository<Customer, String> {

    fun findByFirstNameAndLastNameIgnoreCase(firstName: String, lastName: String): Flux<Customer>

}
