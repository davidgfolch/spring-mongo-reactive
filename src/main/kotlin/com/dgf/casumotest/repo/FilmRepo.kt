package com.dgf.casumotest.repo;

import com.dgf.casumotest.model.Film;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface FilmRepo extends ReactiveMongoRepository<Film, String> {

    Flux<Film> findByTitleIgnoreCaseContaining(String title);
}
