package com.dgf.casumotest.rest;

import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.dgf.casumotest.rest.handler.FilmHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FilmRouter {

    @Bean
    public RouterFunction<ServerResponse> createAllFilms(FilmHandler handler) {
        return route(PUT("/film/createAll").and(accept(TEXT_PLAIN)), handler::create);
    }
    @Bean
    public RouterFunction<ServerResponse> findFilms(FilmHandler handler) {
        return route(GET("/film/find/{title}").and(accept(TEXT_PLAIN)), handler::find);
    }
    @Bean
    public RouterFunction<ServerResponse> listFilms(FilmHandler handler) {
        return route(GET("/film/list").and(accept(TEXT_PLAIN)), handler::list);
    }
}
