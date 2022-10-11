package com.randered.imdb.web;

import com.randered.imdb.domain.rating.ratingDTO.RatingDto;
import com.randered.imdb.domain.rating.service.RatingService;
import com.randered.imdb.domain.role.IsUser;
import com.randered.imdb.util.validation.ValidationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.randered.imdb.util.common.Constants.*;

@RestController
@RequestMapping(BASE_PATH + PATH_USER)
@RequiredArgsConstructor
@Validated
@IsUser
public class UserMovieController {

    private final RatingService ratingService;

    @PostMapping(RATE_PATH)
    public ResponseEntity<ValidationResponse> postRating(@Valid @RequestBody RatingDto ratingDto) {
        ratingService.rateMovie(ratingDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ValidationResponse(SUCCESSFUL_RATING, HttpStatus.CREATED));
    }
}
