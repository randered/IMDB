package com.imdb.repository;

import com.imdb.base.BaseTest;
import com.imdb.domain.movie.entity.Movie;
import com.imdb.domain.movie.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestMovieRepository extends BaseTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void testCRUDMovie() {
        Movie movie = movieRepository.save(buildMovie("Test1"));
        final Movie created = movieRepository.save(movie);
        assertEquals(movie.getName(), created.getName());
        assertEquals(movie.getGenre(), created.getGenre());
        assertEquals(movie.getYear(), created.getYear());
        assertEquals(movie.getAverageRating(), created.getAverageRating());
        movie = movieService.findById(created.getId());
        assertNotNull(movie);
    }

    @Test
    void testFindByName() {
        final String movieName = "Thor";
        final Movie movie = movieRepository.findByName(movieName).orElse(null);
        Assertions.assertEquals(movie.getName(), movieName);
        System.out.println(movie.getRatings().size());
    }

    @Test
    void testFindAll() {
        final List<Movie> movies = movieRepository.findAll();
        Assertions.assertFalse(movies.isEmpty());
    }
}
