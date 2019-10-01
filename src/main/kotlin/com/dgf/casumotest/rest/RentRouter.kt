package com.dgf.casumotest.rest

import com.dgf.casumotest.rest.handler.RentHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class RentRouter {

    @Bean
    fun rentFilms(handler: RentHandler): RouterFunction<ServerResponse> = route(POST("/rent")
            .and(accept(APPLICATION_JSON))
            .and(contentType(APPLICATION_JSON)), HandlerFunction<ServerResponse> { handler.rentFilms(it) })

    @Bean
    fun returnFilms(handler: RentHandler): RouterFunction<ServerResponse> = route(POST("/return/{returnDays}")
            .and(accept(APPLICATION_JSON))
            .and(contentType(APPLICATION_JSON)), HandlerFunction<ServerResponse> { handler.returnFilms(it) })
}
