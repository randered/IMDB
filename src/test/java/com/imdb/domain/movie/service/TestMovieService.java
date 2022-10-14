package com.imdb.domain.movie.service;

import com.imdb.base.BaseTest;
import com.imdb.domain.movie.dto.MovieDto;
import com.imdb.domain.movie.entity.Movie;
import com.imdb.domain.movie.filter.MovieFilter;
import com.imdb.domain.movie.repository.MovieRepository;
import com.imdb.domain.rating.entity.Rating;
import com.imdb.domain.rating.repository.RatingRepository;
import com.imdb.domain.user.entity.User;
import com.imdb.util.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TestMovieService extends BaseTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RatingRepository ratingRepository;

    private Movie movie;
    private Movie created;

    @BeforeEach
    public void before() {
        movie = movieRepository.save(buildMovie("Test1"));
        created = movieRepository.save(movie);
    }

    @Test
    @DisplayName("Given all parameters to filter will return expected movies count")
    void testGetFilteredMoviesWithAllParams() {
        // Given
        final int expectedCount = 1;
        final MovieFilter filter = buildFilter(movie.getName(), movie.getGenre(), movie.getYear());
        final Request<MovieFilter> request = buildMovieRequest(filter);
        // When
        final Page<MovieDto> moviesPage = movieService.getFilteredMovies(request);
        final int count = moviesPage.getContent().size();
        // Then
        assertEquals(expectedCount, count);
    }

    @Test
    @DisplayName("Given one parameter to filter will return expected movies count")
    void testGetFilteredMoviesWithOneParam() {
        // Given
        final int expectedCount = 2;
        final MovieFilter filter = buildFilter("T", null, null);
        final Request<MovieFilter> request = buildMovieRequest(filter);
        // When
        final Page<MovieDto> moviesPage = movieService.getFilteredMovies(request);
        final int count = moviesPage.getContent().size();
        // Then
        assertEquals(expectedCount, count);
    }

    @Test
    void testUploadImage() throws IOException {
        final File file = new File("src/test/resources/thor.jpg");
        final FileInputStream inputStream = new FileInputStream(file);
        final MultipartFile multipartFile = new MockMultipartFile("image", file.getName(),
                "image/png", inputStream.readAllBytes());
        assertNull(movieService.findByName(movie.getName()).getImage());
        movieService.addImageToMovie(created.getName(), multipartFile);
        assertNotNull(movieService.findByName(created.getName()).getImage());
    }

    @Test
    void testAverageRatings() {
        assertEquals(4.0, movie.getAverageRating());
        final User user = userService.update(buildUser());
        final User secondUser = userService.update(buildUser("Test5", "Test5", "user"));
        final Rating rating = ratingRepository.save(buildRating(user, movie));
        final Rating secondRating = buildRating(secondUser, movie);
        secondRating.setRating(3.0);
        ratingRepository.save(secondRating);

        movie.getRatings().add(rating);
        movie.getRatings().add(secondRating);
        movie.loadAverage();
        movieRepository.save(movie);

        assertEquals(4, movie.getAverageRating());
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
        request.setPageNumber(0);
        request.setPageSize(1);
        return request;
    }
}