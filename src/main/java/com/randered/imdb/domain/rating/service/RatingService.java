package com.randered.imdb.domain.rating.service;

import com.randered.imdb.domain.rating.entity.Rating;
import com.randered.imdb.domain.rating.repository.RatingRepository;
import com.randered.imdb.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    public void update(final Rating rating) {
        ratingRepository.save(rating);
    }

    public Optional<Rating> findById(final Integer id) {
        return ratingRepository.findById(id);
    }

    public Optional<Rating> findByName(final User user) {
        return ratingRepository.findByUser(user);
    }

}
