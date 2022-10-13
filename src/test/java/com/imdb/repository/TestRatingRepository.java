package com.imdb.repository;

import com.imdb.base.BaseTest;
import com.imdb.domain.movie.entity.Movie;
import com.imdb.domain.movie.repository.MovieRepository;
import com.imdb.domain.rating.entity.Rating;
import com.imdb.domain.rating.repository.RatingRepository;
import com.imdb.domain.user.entity.User;
import com.imdb.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRatingRepository extends BaseTest {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    void testFindAllByUser() {
        final Movie movie = buildMovie("New Movie");
        movieRepository.save(movie);

        final User user = buildUser();
        userRepository.save(user);

        final Rating rating = buildRating(user, movie);
        ratingRepository.save(rating);

        final Rating rating2 = buildRating(user, movie);
        ratingRepository.save(rating2);

        final List<Rating> ratings = ratingRepository.findAllByUser(user);
        assertEquals(2, ratings.size());
    }
}
