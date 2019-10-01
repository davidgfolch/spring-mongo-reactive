package com.dgf.casumotest.service;

import com.dgf.casumotest.model.Film;
import com.dgf.casumotest.repo.FilmRepo;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class FilmService {

    @Autowired
    private FilmRepo repo;

    public Flux<Film> findByTitleIgnoreCaseContaining(String title) {
        log.info("finding titles like: {}", title);
        return repo.findByTitleIgnoreCaseContaining(title);
    }

    public Flux<Film> createFilms(List<Film> films) {
        return repo.deleteAll().thenMany(
            repo.saveAll(films)
        );
    }

    Flux<Film> increaseStock(List<String> films) {
        log.info("increase stock {} ", films);
        Flux<Film> updatedFilms = repo.findAllById(films)
            .map(Film::availabilityInc);
        log.info("increase stock save films {} ", updatedFilms);
        return repo.saveAll(updatedFilms);
    }

    Flux<Film> decreaseStock(@NotNull List<String> films) {
        log.info("decrease stock {} ", films);
        Flux<Film> availableFilms = repo.findAllById(films).map(Film::availabilityDec)
            .filter(Optional::isPresent)
            .map(Optional::get);
        log.info("decrease stock save availableFilms {} ", availableFilms);
        return repo.saveAll(availableFilms);
    }

    public Flux<Film> findAll() {
        return repo.findAll();
    }

}
