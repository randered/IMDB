package com.randered.imdb.domain.movie.repository;

import com.randered.imdb.domain.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

    Optional<Movie> findById(Integer id);

    Optional<Movie> findByName(String name);

    List<Movie> findAll();
}
