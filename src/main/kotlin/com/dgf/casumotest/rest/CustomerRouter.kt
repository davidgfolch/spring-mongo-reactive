package com.dgf.casumotest.rest;

import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.dgf.casumotest.rest.handler.CustomerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CustomerRouter {

    private static final String CUSTOMER = "/customer";

    @Bean
    public RouterFunction<ServerResponse> createAllCustomers(CustomerHandler handler) {
        return route(PUT(CUSTOMER+"/createAll").and(accept(TEXT_PLAIN)), handler::createAll);
    }
    @Bean
    public RouterFunction<ServerResponse> find(CustomerHandler handler) {
        return route(GET(CUSTOMER +"/find/{firstName}/{lastName}").and(accept(TEXT_PLAIN)), handler::find);
    }

    @Bean
    public RouterFunction<ServerResponse> get(CustomerHandler handler) {
        return route(GET(CUSTOMER +"/get/{id}").and(accept(TEXT_PLAIN)), handler::get);
    }
}
