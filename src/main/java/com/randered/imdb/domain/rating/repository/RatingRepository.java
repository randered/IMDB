package com.randered.imdb.domain.rating.repository;

import com.randered.imdb.domain.rating.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

}
