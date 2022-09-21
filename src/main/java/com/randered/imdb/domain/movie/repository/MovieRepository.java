package com.randered.imdb.domain.movie.repository;

import com.randered.imdb.domain.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    @Override
    @Nonnull
    Optional<Movie> findById(@Nonnull Integer id);

    @Nonnull
    List<Movie> findAll();
}
