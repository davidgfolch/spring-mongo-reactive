package com.dgf.casumotest.service

import com.dgf.casumotest.model.Customer
import com.dgf.casumotest.model.CustomerFilm
import com.dgf.casumotest.model.Film
import com.dgf.casumotest.model.calculated.Surcharges
import com.dgf.casumotest.repo.CustomerRepo
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import javax.validation.constraints.NotNull

private val log = KotlinLogging.logger {}

@Service
class CustomerService @Autowired constructor(
        private val repo: CustomerRepo,
        private val calcService: CalcService
) {

    fun findByFirstNameAndLastNameIgnoreCase(firstName: String, lastName: String): Flux<Customer> =
            repo.findByFirstNameAndLastNameIgnoreCase(firstName, lastName)

    fun findById(id: String): Mono<Customer> = repo.findById(id)

    fun getById(id: String): Mono<Customer> = repo.findById(id)

    fun save(entity: Customer): Mono<Customer> = repo.save(entity)

    fun createCustomers(customers: List<Customer>): Flux<Customer> =
            repo.deleteAll().thenMany(
                    repo.saveAll(customers)
            )

    fun bookFilms(@NotNull customer: Customer, films: List<Film>): Mono<Customer> {
        val now = Calendar.getInstance().time
        customer.films.addAll(films.map { film -> CustomerFilm(film.id, now, film.rentalType) })
        customer.copy(points = (customer.points + calcService.getPoints(films)))
        log.info("customer films update {} ", customer)
        return save(customer)
    }

    fun returnFilms(@NotNull customer: Customer, returnDays: Int, films: List<String>): Mono<Surcharges> {
        val olderCustomerFilms = customer.films.sortedBy { customerFilm: CustomerFilm -> customerFilm.date }
                .filter { (id) -> films.contains(id) }
                .subList(0, films.size)
        val surcharges = calcService.getSurcharges(olderCustomerFilms, returnDays)
        customer.films.removeAll(olderCustomerFilms)
        return repo.save(customer).then(Mono.just(surcharges))
    }
}
