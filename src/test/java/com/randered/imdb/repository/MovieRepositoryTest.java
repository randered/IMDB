package com.randered.imdb.repository;

import com.randered.imdb.base.BaseTest;
import com.randered.imdb.domain.movie.entity.Movie;
import com.randered.imdb.domain.movie.repository.MovieRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MovieRepositoryTest extends BaseTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void testCRUDMovie() {
        Movie movie = movieRepository.save(buildMovie("Test1"));
        Movie created = movieRepository.save(movie);
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
