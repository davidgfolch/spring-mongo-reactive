package com.dgf.casumotest.rest.handler;

import static com.dgf.casumotest.Constants.FILMS;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.dgf.casumotest.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class FilmHandler {

    @Autowired
    private FilmService service;

    public Mono<ServerResponse> create(ServerRequest request) {
        return service.createFilms(FILMS.get()).collectList().flatMap(resp->
            ok().contentType(APPLICATION_JSON_UTF8).body(fromObject(resp))
        );
    }

    public Mono<ServerResponse> find(ServerRequest request) {
        return service.findByTitleIgnoreCaseContaining(request.pathVariable("title")).collectList().flatMap(resp->
            ok().contentType(APPLICATION_JSON_UTF8).body(fromObject(resp))
        );
    }

    public Mono<ServerResponse> list(ServerRequest request) {
        return service.findAll().collectList().flatMap(resp->
            ok().contentType(APPLICATION_JSON_UTF8).body(fromObject(resp))
        );
    }
}