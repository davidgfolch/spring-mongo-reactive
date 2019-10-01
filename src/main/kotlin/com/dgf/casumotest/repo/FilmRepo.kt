package com.dgf.casumotest.repo

import com.dgf.casumotest.model.Film
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface FilmRepo : ReactiveMongoRepository<Film, String> {

    fun findByTitleIgnoreCaseContaining(title: String): Flux<Film>
}
