package com.randered.imdb.domain.movie.service;

import com.randered.imdb.base.BaseTest;
import com.randered.imdb.domain.movie.entity.Movie;
import com.randered.imdb.domain.movie.filter.MovieFilter;
import com.randered.imdb.domain.movie.movieDTO.MovieDto;
import com.randered.imdb.domain.movie.repository.MovieRepository;
import com.randered.imdb.util.Request;
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

class MovieServiceTest extends BaseTest {

    @Autowired
    private MovieRepository movieRepository;

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