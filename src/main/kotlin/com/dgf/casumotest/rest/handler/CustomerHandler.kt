package com.dgf.casumotest.rest.handler;

import static com.dgf.casumotest.Constants.CUSTOMERS;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.dgf.casumotest.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CustomerHandler {

    @Autowired
    private CustomerService service;

    public Mono<ServerResponse> createAll(ServerRequest request) {
        return service.createCustomers(CUSTOMERS.get()).collectList().flatMap(resp->
            ok().contentType(APPLICATION_JSON_UTF8).body(fromObject(resp))
        );
    }

    public Mono<ServerResponse> find(ServerRequest req) {
        return service.findByFirstNameAndLastNameIgnoreCase(
            req.pathVariable("firstName"),
            req.pathVariable("lastName"))
        .collectList().flatMap(customers ->
            ok().contentType(APPLICATION_JSON_UTF8).body(fromObject(customers))
        );
    }

    public Mono<ServerResponse> get(ServerRequest req) {
        return service.findById(req.pathVariable("id")).flatMap(customer ->
            ok().contentType(APPLICATION_JSON_UTF8).body(fromObject(customer))
        );
    }
}