package com.dgf.casumotest.service

import com.dgf.casumotest.model.Film
import com.dgf.casumotest.repo.FilmRepo
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.util.*
import javax.validation.constraints.NotNull

private val log = KotlinLogging.logger {}

@Service
class FilmService @Autowired constructor(
        private val repo: FilmRepo
) {

    fun findByTitleIgnoreCaseContaining(title: String): Flux<Film> = repo.findByTitleIgnoreCaseContaining(title)

    fun createFilms(films: List<Film>): Flux<Film> = repo.deleteAll().thenMany(repo.saveAll(films))

    internal fun increaseStock(films: List<String>): Flux<Film> {
        log.info("increase stock {} ", films)
        val updatedFilms = repo.findAllById(films).map { it.availabilityInc() }
        log.info("increase stock save films {} ", updatedFilms)
        return repo.saveAll(updatedFilms)
    }

    internal fun decreaseStock(@NotNull films: List<String>): Flux<Film> {
        log.info("decrease stock {} ", films)
        val availableFilms = repo.findAllById(films)
                .map { it.availabilityDec() }
                .filter(Optional<*>::isPresent)
                .map { film -> film.get() }
        log.info("decrease stock save availableFilms {} ", availableFilms)
        return repo.saveAll(availableFilms)
    }

    fun findAll(): Flux<Film> {
        return repo.findAll()
    }

}
