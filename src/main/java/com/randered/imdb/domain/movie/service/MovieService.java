package com.randered.imdb.domain.movie.service;

import com.randered.imdb.domain.movie.entity.Movie;
import com.randered.imdb.domain.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public void update(final Movie movie) {
        movieRepository.save(movie);
    }

    public Optional<Movie> findById(final Integer id) {
        return movieRepository.findById(id);
    }

    public Optional<Movie> findByName(final String username) {
        return movieRepository.findByName(username);
    }

}
