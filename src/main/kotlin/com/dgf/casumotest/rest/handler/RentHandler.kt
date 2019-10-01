package com.dgf.casumotest.rest.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import com.dgf.casumotest.model.UserFilmsRequest;
import com.dgf.casumotest.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class RentHandler {

    private static final Mono<ServerResponse> BAD_REQUEST = ServerResponse.badRequest().build();

    @Autowired
    private RentService service;

    public Mono<ServerResponse> rentFilms(ServerRequest request) {
        return request.bodyToMono(UserFilmsRequest.class)
            .flatMap(req->service.rentFilms(req))
            .flatMap(res->ok().contentType(APPLICATION_JSON).body(fromObject(res)))
            .switchIfEmpty(BAD_REQUEST);
    }

    public Mono<ServerResponse> returnFilms(ServerRequest request) {
        return request.bodyToMono(UserFilmsRequest.class)
            .flatMap(req->service.returnFilms(req,Integer.parseInt(request.pathVariable("returnDays"))))
            .flatMap(res->ok().contentType(APPLICATION_JSON).body(fromObject(res)))
            .switchIfEmpty(BAD_REQUEST);
    }

}