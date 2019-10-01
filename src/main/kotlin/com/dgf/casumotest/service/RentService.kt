package com.dgf.casumotest.service;

import static com.dgf.casumotest.Constants.CUSTOMERS;
import static com.dgf.casumotest.Constants.FILMS;
import static com.dgf.casumotest.util.Json.toJson;
import static java.lang.String.format;

import com.dgf.casumotest.model.Customer;
import com.dgf.casumotest.model.Film;
import com.dgf.casumotest.model.UserFilmsRequest;
import com.dgf.casumotest.model.calculated.RentResult;
import com.dgf.casumotest.model.calculated.RentResultLine;
import com.dgf.casumotest.model.calculated.Surcharges;
import com.dgf.casumotest.util.Json;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class RentService {

    @Autowired
    private CalcService calcService;
    @Autowired
    private FilmService filmService;
    @Autowired
    private CustomerService customerService;

    //todo transactional annotation not working https://spring.io/blog/2019/05/16/reactive-transactions-with-spring
    public Mono<RentResult> rentFilms(UserFilmsRequest rentReq) {
        return customerService.getById(rentReq.getUserId())
            .flatMap(customer ->
                filmService.decreaseStock(rentReq.getFilms()).collectList()
                    .flatMap(availableFilms -> customerService.bookFilms(customer,availableFilms)
                        .flatMap(savedCustomer-> {
                            List<RentResultLine> availableFilmsPrice = calcService.getPrice(availableFilms);
                            double total = availableFilmsPrice.parallelStream().mapToDouble(RentResultLine::getPrice).sum();
                            RentResult rentResult = new RentResult(availableFilmsPrice, total);
                            return Mono.just(rentResult);
                        })
                    )
            ).switchIfEmpty(Mono.error(new UserNotFoundException(format("UserId '%s' not found", rentReq.getUserId()))));
    }

    //todo transactional annotation not working https://spring.io/blog/2019/05/16/reactive-transactions-with-spring
    public Mono<Surcharges> returnFilms(UserFilmsRequest returnReq, int returnDays) {
        return customerService.getById(returnReq.getUserId())
            .flatMap(customer -> {
                List<String> customerFilms = returnReq.getFilms().parallelStream()
                    .filter(filmId-> customer.getFilms().stream().anyMatch(cf->filmId.equals(cf.getId())))
                    .collect(Collectors.toList());
                return filmService.increaseStock(customerFilms).collectList()
                    .flatMap(films -> customerService.returnFilms(customer,returnDays,films.parallelStream().map(Film::getId).collect(Collectors.toList())));
                }
            ).switchIfEmpty(Mono.error(new UserNotFoundException(format("UserId '%s' not found", returnReq.getUserId()))));
    }

    public void initData() {
        log.warn("Auto generating data for films & customers.");
        filmService.createFilms(FILMS.get()).map(Film::getId)
            .collectList().map(films ->
                customerService.createCustomers(CUSTOMERS.get()).map(Customer::getId)
                    .map(customer -> new UserFilmsRequest(customer, films))
            ).subscribe(map -> map.subscribe(map2->log.warn("initData generated request:\n" + toJson(map2))));
    }

}
