package com.dgf.casumotest.rest

import com.dgf.casumotest.rest.handler.FilmHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.TEXT_PLAIN
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class FilmRouter {

    @Bean
    fun createAllFilms(handler: FilmHandler): RouterFunction<ServerResponse> = route(PUT("/film/createAll")
            .and(accept(TEXT_PLAIN)), HandlerFunction<ServerResponse> { handler.create(it) })

    @Bean
    fun findFilms(handler: FilmHandler): RouterFunction<ServerResponse> = route(GET("/film/find/{title}")
            .and(accept(TEXT_PLAIN)), HandlerFunction<ServerResponse> { handler.find(it) })

    @Bean
    fun listFilms(handler: FilmHandler): RouterFunction<ServerResponse> = route(GET("/film/list")
            .and(accept(TEXT_PLAIN)), HandlerFunction<ServerResponse> { handler.list(it) })

}
