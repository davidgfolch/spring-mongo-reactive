package com.dgf.casumotest.rest.handler

import com.dgf.casumotest.model.UserFilmsRequest
import com.dgf.casumotest.model.calculated.RentResult
import com.dgf.casumotest.model.calculated.Surcharges
import com.dgf.casumotest.service.RentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class RentHandler @Autowired constructor(
        private val service: RentService
) {

    companion object {
        private val BAD_REQUEST = ServerResponse.badRequest().build()
    }

    fun rentFilms(request: ServerRequest): Mono<ServerResponse> = request.bodyToMono(UserFilmsRequest::class.java)
            .flatMap<RentResult> { req -> service.rentFilms(req) }
            .flatMap { res -> ok().contentType(APPLICATION_JSON).body(fromObject<RentResult>(res)) }
            .switchIfEmpty(BAD_REQUEST)

    fun returnFilms(request: ServerRequest): Mono<ServerResponse> = request.bodyToMono(UserFilmsRequest::class.java)
            .flatMap<Surcharges> { req -> service.returnFilms(req, Integer.parseInt(request.pathVariable("returnDays"))) }
            .flatMap { res -> ok().contentType(APPLICATION_JSON).body(fromObject<Surcharges>(res)) }
            .switchIfEmpty(BAD_REQUEST)

}