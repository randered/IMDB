package com.randered.imdb.domain.user.controllers;

import com.randered.imdb.domain.rating.ratingDTO.RatingDto;
import com.randered.imdb.domain.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

import static com.randered.imdb.util.common.Constants.*;

@RestController
@RequestMapping(PATH_USER)
@RequiredArgsConstructor
@Validated
public class UserMovieController {

    private final RatingService ratingService;

    @PostMapping(RATE_PATH)
    public ResponseEntity<String> postRating(@Valid @RequestBody RatingDto ratingDto, Principal principal) {
        ratingService.rateMovie(ratingDto, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SUCCESSFUL_RATING + HttpStatus.CREATED);
    }
}
