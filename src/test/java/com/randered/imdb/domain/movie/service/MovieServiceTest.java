package com.randered.imdb.domain.movie.service;

import com.randered.imdb.base.BaseTest;
import com.randered.imdb.domain.movie.entity.Movie;
import com.randered.imdb.domain.movie.filter.MovieFilter;
import com.randered.imdb.domain.movie.movieDTO.MovieDto;
import com.randered.imdb.domain.movie.repository.MovieRepository;
import com.randered.imdb.util.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieServiceTest extends BaseTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    private Movie movie;
    private Movie created;

    @BeforeEach
    public void before() {
        movie = movieService.createMovie(buildMovieDto());
        created = movieService.saveOrGet(movie);
    }

    @Test
    public void testCRUDMovie() {
        assertEquals(movie.getName(), created.getName());
        assertEquals(movie.getGenre(), created.getGenre());
        assertEquals(movie.getYear(), created.getYear());
        assertEquals(movie.getAverageRating(), created.getAverageRating());

        final Optional<Movie> movieOpt = movieService.findById(created.getId());
        assertTrue(movieOpt.isPresent());
    }

    @Test
    void testGetFilteredMovies() {
        MovieFilter filter = buildMovieFilter(movie);
        Request<MovieFilter> request = buildMovieRequest(filter);
        Page<MovieDto> moviesPage = movieService.getFilteredMovies(request);

        assertEquals(movie.getName(), moviesPage.getContent().get(0).getName());
        int count = moviesPage.getContent().size();
        assertEquals(1, count);

        MovieFilter filter2 = buildFilter("o", null, null);
        Request<MovieFilter> request2 = buildMovieRequest(filter2);
        Page<MovieDto> moviesPage2 = movieService.getFilteredMovies(request2);
        int count2 = moviesPage2.getContent().size();
        assertEquals(3, count2);
    }

    @Test
    void testGetAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        assert !movies.isEmpty();
        int count = movies.size();
        assertEquals(3, count);
    }

    private MovieFilter buildMovieFilter(final Movie movie) {
        return MovieFilter.builder()
                .name(movie.getName())
                .genre(movie.getGenre())
                .year(movie.getYear())
                .build();
    }

    private MovieFilter buildFilter(final String name, final String genre, final Integer year) {
        return MovieFilter.builder()
                .name(name)
                .genre(genre)
                .year(year)
                .build();
    }

    private Request<MovieFilter> buildMovieRequest(final MovieFilter filter) {
        Request<MovieFilter> request = new Request<>();
        request.setFilter(filter);
        request.setPageNumber(1);
        request.setPageSize(1);
        return request;
    }
}