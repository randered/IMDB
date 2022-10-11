package com.randered.imdb.domain.rating.service;

import com.randered.imdb.customExceptions.MovieAlreadyRatedByUserException;
import com.randered.imdb.domain.movie.entity.Movie;
import com.randered.imdb.domain.movie.service.MovieService;
import com.randered.imdb.domain.rating.entity.Rating;
import com.randered.imdb.domain.rating.ratingDTO.RatingDto;
import com.randered.imdb.domain.rating.repository.RatingRepository;
import com.randered.imdb.domain.user.entity.User;
import com.randered.imdb.domain.user.service.UserAuthenticationService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;
    private final MovieService movieService;
    private final UserAuthenticationService userAuthenticationService;

    public void update(final Rating rating) {
        ratingRepository.save(rating);
    }

    public Optional<Rating> findById(final Integer id) {
        return ratingRepository.findById(id);
    }

    public void rateMovie(@NotNull final RatingDto ratingDto) {
        final Movie movie = movieService.findByName(ratingDto.getMovieName());
        final User user = userAuthenticationService.getContextHolder();
        final Rating rating = new Rating(user, movie, ratingDto.getRating(), ratingDto.getComment());

        try {
            ratingRepository.save(rating);
        } catch (DataIntegrityViolationException e) {
            throw new MovieAlreadyRatedByUserException();
        }
    }

}
