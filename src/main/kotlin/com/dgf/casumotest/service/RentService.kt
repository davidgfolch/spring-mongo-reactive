package com.dgf.casumotest.service

import com.dgf.casumotest.Constants.CUSTOMERS
import com.dgf.casumotest.Constants.FILMS
import com.dgf.casumotest.model.UserFilmsRequest
import com.dgf.casumotest.model.calculated.RentResult
import com.dgf.casumotest.model.calculated.Surcharges
import com.dgf.casumotest.util.Json.toJson
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.String.format


private val log = KotlinLogging.logger {}

@Service
class RentService @Autowired constructor(
        private val calcService: CalcService,
        private val filmService: FilmService,
        private val customerService: CustomerService
) {

    //todo transactional annotation not working https://spring.io/blog/2019/05/16/reactive-transactions-with-spring
    fun rentFilms(rentReq: UserFilmsRequest): Mono<RentResult> =
            customerService.getById(rentReq.userId)
                    .flatMap { customer ->
                        filmService.decreaseStock(rentReq.films).collectList().flatMap { availableFilms ->
                            customerService.bookFilms(customer, availableFilms)
                                    .flatMap { _ ->
                                        val availableFilmsPrice = calcService.getPrice(availableFilms)
                                        val total = availableFilmsPrice.map { it.price }.sum()
                                        val rentResult = RentResult(availableFilmsPrice, total)
                                        Mono.just(rentResult)
                                    }
                        }
                    }.switchIfEmpty(Mono.error(UserNotFoundException(format("UserId '%s' not found", rentReq.userId))))

    //todo transactional annotation not working https://spring.io/blog/2019/05/16/reactive-transactions-with-spring
    fun returnFilms(returnReq: UserFilmsRequest, returnDays: Int): Mono<Surcharges> =
            customerService.getById(returnReq.userId)
                    .flatMap { customer ->
                        returnReq.films.filter { filmId -> customer.films.filter { cf -> filmId == cf.id }.any() }
                                .let { customerFilms ->
                                    filmService.increaseStock(customerFilms).collectList().flatMap { films ->
                                        customerService.returnFilms(customer, returnDays, films.map { it::id.get() }.toList())
                                    }
                                }
                    }.switchIfEmpty(Mono.error(UserNotFoundException(format("UserId '%s' not found", returnReq.userId))))

    fun initData() {
        log.info("Auto generating data for films & customers.")
        filmService.createFilms(FILMS).map { it::id.get() }.collectList().map { filmsIds ->
            customerService.createCustomers(CUSTOMERS).map { it::id.get() }.map { customerId ->
                log.info("initData generated request:\n" + toJson(UserFilmsRequest(customerId, filmsIds)))
            }.subscribe()
        }.subscribe()
    }

}
