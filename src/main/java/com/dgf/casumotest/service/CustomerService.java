package com.dgf.casumotest.service;

import com.dgf.casumotest.model.Customer;
import com.dgf.casumotest.model.CustomerFilm;
import com.dgf.casumotest.model.Film;
import com.dgf.casumotest.model.calculated.Surcharges;
import com.dgf.casumotest.repo.CustomerRepo;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    private CustomerRepo repo;
    @Autowired
    private CalcService calcService;

    public Flux<Customer> findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName) {
        return repo.findByFirstNameAndLastNameIgnoreCase(firstName,lastName);
    }

    public Mono<Customer> findById(String id) {
        return repo.findById(id);
    }

    Mono<Customer> getById(String id) {
        return repo.findById(id);
    }

    Mono<Customer> save(Customer entity) {
        return repo.save(entity);
    }

    Flux<Customer> findAll() {
        return repo.findAll();
    }

    public Flux<Customer> createCustomers(List<Customer> customers) {
        return repo.deleteAll().thenMany(
            repo.saveAll(customers)
        );
    }

    Mono<Customer> bookFilms(@NotNull Customer customer, List<Film> films) {
        Date now = Calendar.getInstance().getTime();
        customer.getFilms().addAll(films.parallelStream().map(film->new CustomerFilm(film.getId(),now, film.getRentalType())).collect(Collectors.toList()));
        customer.setPoints(customer.getPoints()+calcService.getPoints(films));
        log.info("customer films update {} ", customer);
        return save(customer);
    }

    Mono<Surcharges> returnFilms(@NotNull Customer customer, int returnDays, List<String> films) {
        List<CustomerFilm> olderCustomerFilms = customer.getFilms().parallelStream()
            .sorted(Comparator.comparing(CustomerFilm::getDate))
            .filter(film -> films.contains(film.getId()))
            .limit(films.size())
            .collect(Collectors.toList());
        Surcharges surcharges = calcService.getSurcharges(olderCustomerFilms,returnDays);
        customer.getFilms().removeAll(olderCustomerFilms);
        return repo.save(customer).then(Mono.just(surcharges));
    }
}
