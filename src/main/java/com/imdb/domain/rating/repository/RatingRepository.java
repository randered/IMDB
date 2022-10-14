package com.imdb.domain.rating.repository;

import com.imdb.domain.rating.entity.Rating;
import com.imdb.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
        List<Rating> findAllByUser(final User user);
}
