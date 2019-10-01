package com.dgf.casumotest.rest.handler

import com.dgf.casumotest.Constants
import com.dgf.casumotest.model.Customer
import com.dgf.casumotest.service.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class CustomerHandler @Autowired constructor(
        private val service: CustomerService
) {

    fun createAll(request: ServerRequest): Mono<ServerResponse> = service.createCustomers(Constants.CUSTOMERS)
            .collectList()
            .flatMap { resp -> ok().contentType(APPLICATION_JSON_UTF8).body(fromObject<List<Customer>>(resp)) }

    fun find(req: ServerRequest): Mono<ServerResponse> =
            service.findByFirstNameAndLastNameIgnoreCase(
                    req.pathVariable("firstName"),
                    req.pathVariable("lastName")
            ).collectList().flatMap { customers -> ok().contentType(APPLICATION_JSON_UTF8).body(fromObject<List<Customer>>(customers)) }

    operator fun get(req: ServerRequest): Mono<ServerResponse> = service.findById(req.pathVariable("id"))
            .flatMap { customer -> ok().contentType(APPLICATION_JSON_UTF8).body(fromObject<Customer>(customer)) }

}