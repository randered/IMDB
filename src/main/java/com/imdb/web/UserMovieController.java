package com.imdb.web;

import com.imdb.domain.rating.dto.RatingDto;
import com.imdb.domain.rating.service.RatingService;
import com.imdb.domain.role.IsUser;
import com.imdb.util.common.Constants;
import com.imdb.util.validation.ValidationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(Constants.BASE_PATH + Constants.PATH_USER)
@RequiredArgsConstructor
@Validated
@IsUser
@Log4j2
public class UserMovieController {

    private final RatingService ratingService;

    @PostMapping(Constants.RATE_PATH)
    public ResponseEntity<ValidationResponse> postRating(@Valid @RequestBody RatingDto ratingDto) {
        ratingService.rateMovie(ratingDto);
        log.info("Movie with name {} was rated", ratingDto.getMovieName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ValidationResponse(Constants.SUCCESSFUL_RATING, HttpStatus.CREATED));
    }
}
