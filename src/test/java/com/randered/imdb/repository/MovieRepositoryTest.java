package com.randered.imdb.repository;

import com.randered.imdb.base.BaseTest;
import com.randered.imdb.domain.movie.entity.Movie;
import com.randered.imdb.domain.movie.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MovieRepositoryTest extends BaseTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void testFindByName() {
        final String movieName = "Thor";
        Movie movie = movieRepository.findByName(movieName).orElse(null);
        Assertions.assertEquals(movie.getName(), movieName);
        System.out.println(movie.getRatings().size());
    }

    @Test
    void testFindAll() {
        List<Movie> movies = movieRepository.findAll();
        Assertions.assertFalse(movies.isEmpty());
    }
}
