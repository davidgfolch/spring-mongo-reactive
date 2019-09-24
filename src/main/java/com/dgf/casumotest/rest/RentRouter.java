package com.dgf.casumotest.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.dgf.casumotest.rest.handler.RentHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RentRouter {

    @Bean
    public RouterFunction<ServerResponse> rentFilms(RentHandler handler) {
        return route(POST("/rent").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), handler::rentFilms);
    }
    @Bean
    public RouterFunction<ServerResponse> returnFilms(RentHandler handler) {
        return route(POST("/return/{returnDays}").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), handler::returnFilms);
    }
}
