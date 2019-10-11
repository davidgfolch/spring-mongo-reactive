package com.dgf.casumotest.rest

import com.dgf.casumotest.rest.handler.CustomerHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.TEXT_PLAIN
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration
class CustomerRouter {

    companion object {
        private const val customer = "/customer"
    }

    @Bean
    fun createAllCustomers(handler: CustomerHandler): RouterFunction<ServerResponse> {
        return route(PUT("$customer/createAll").and(accept(TEXT_PLAIN)), HandlerFunction<ServerResponse> { handler.createAll(it) })
    }

    @Bean
    fun find(handler: CustomerHandler): RouterFunction<ServerResponse> {
        return route(GET("$customer/find/{firstName}/{lastName}").and(accept(TEXT_PLAIN)), HandlerFunction<ServerResponse> { handler.find(it) })
    }

    @Bean
    operator fun get(handler: CustomerHandler): RouterFunction<ServerResponse> {
        return route(GET("$customer/get/{id}").and(accept(TEXT_PLAIN)), HandlerFunction<ServerResponse> { handler[it] })
    }

}
