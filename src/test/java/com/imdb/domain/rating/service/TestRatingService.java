package com.imdb.domain.rating.service;

import com.imdb.base.BaseTest;
import com.imdb.domain.movie.entity.Movie;
import com.imdb.domain.movie.service.MovieService;
import com.imdb.domain.rating.dto.RatingDto;
import com.imdb.domain.rating.entity.Rating;
import com.imdb.domain.rating.repository.RatingRepository;
import com.imdb.domain.user.entity.User;
import com.imdb.domain.user.service.UserAuthenticationService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TestRatingService extends BaseTest {

    @MockBean
    private UserAuthenticationService authenticationService;

    @MockBean
    private MovieService movieService;

    @MockBean
    private RatingRepository repository;

    @Autowired
    private RatingService ratingService;

    @Test
    void testRateMovie() {
        final RatingDto ratingDto = new RatingDto();
        ratingDto.setMovieName("The Room");
        ratingDto.setRating(5);

        final User user = buildUser();

        final Movie movie = new Movie();
        movie.setName(ratingDto.getMovieName());


        final ArgumentCaptor<String> movieNameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<Rating> ratingArgumentCaptor = ArgumentCaptor.forClass(Rating.class);

        when(movieService.findByName(anyString())).thenReturn(movie);
        when(authenticationService.getContextHolder()).thenReturn(user);

        ratingService.rateMovie(ratingDto);

        verify(movieService).findByName(movieNameArgumentCaptor.capture());
        verify(repository).save(ratingArgumentCaptor.capture());

        final Rating rating = ratingArgumentCaptor.getValue();

        assertThat(rating.getUser()).isEqualTo(user);
        assertThat(rating.getMovie()).isEqualTo(movie);
        assertThat(rating.getRating()).isEqualTo(ratingDto.getRating());
    }
}
