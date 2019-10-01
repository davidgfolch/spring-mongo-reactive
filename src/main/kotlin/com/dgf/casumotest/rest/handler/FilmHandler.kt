package com.dgf.casumotest.rest.handler

import com.dgf.casumotest.Constants
import com.dgf.casumotest.model.Film
import com.dgf.casumotest.service.FilmService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono

@Component
class FilmHandler @Autowired constructor(
        private val service: FilmService
) {

    fun create(request: ServerRequest): Mono<ServerResponse> = service.createFilms(Constants.FILMS).collectList()
            .flatMap { resp -> ok().contentType(APPLICATION_JSON_UTF8).body(fromObject<List<Film>>(resp)) }

    fun find(request: ServerRequest): Mono<ServerResponse> = service.findByTitleIgnoreCaseContaining(request.pathVariable("title")).collectList()
            .flatMap { resp -> ok().contentType(APPLICATION_JSON_UTF8).body(fromObject<List<Film>>(resp)) }

    fun list(request: ServerRequest): Mono<ServerResponse> = service.findAll().collectList()
            .flatMap { resp -> ok().contentType(APPLICATION_JSON_UTF8).body(fromObject<List<Film>>(resp)) }

}