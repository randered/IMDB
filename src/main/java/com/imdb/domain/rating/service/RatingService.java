package com.imdb.domain.rating.service;

import com.imdb.domain.movie.entity.Movie;
import com.imdb.domain.movie.service.MovieService;
import com.imdb.domain.rating.dto.RatingDto;
import com.imdb.domain.rating.entity.Rating;
import com.imdb.domain.rating.repository.RatingRepository;
import com.imdb.domain.user.entity.User;
import com.imdb.domain.user.service.UserAuthenticationService;
import com.imdb.exceptions.MovieAlreadyRatedByUserException;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final MovieService movieService;
    private final UserAuthenticationService userAuthenticationService;

    public void update(final Rating rating) {
        ratingRepository.save(rating);
    }

    public List<Rating> findAllByUser(final User user) {
        return ratingRepository.findAllByUser(user);
    }

    public void rateMovie(@NotNull final RatingDto ratingDto) {
        final Movie movie = movieService.findByName(ratingDto.getMovieName());
        final User user = userAuthenticationService.getContextHolder();
        final Rating rating = new Rating(user, movie, ratingDto.getRating(), ratingDto.getComment());

        if (!checkIfMovieAlreadyRatedByUser(user, movie)) {
            ratingRepository.save(rating);
            movie.getRatings().add(rating);
            movieService.update(movie);
        } else {
            throw new MovieAlreadyRatedByUserException();
        }
    }

    private boolean checkIfMovieAlreadyRatedByUser(final User user, final Movie movie) {
        final Stream<String> movies = findAllByUser(user).stream().map(rating -> rating.getMovie().getName());
        return movies.anyMatch(s -> s.contains(movie.getName()));
    }
}
