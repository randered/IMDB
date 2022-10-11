package com.randered.imdb.web;

import com.randered.imdb.domain.actor.actorDTO.ActorDto;
import com.randered.imdb.domain.actor.service.ActorService;
import com.randered.imdb.domain.movie.movieDTO.MovieDto;
import com.randered.imdb.domain.movie.service.MovieService;
import com.randered.imdb.domain.rating.entity.Rating;
import com.randered.imdb.domain.rating.repository.RatingRepository;
import com.randered.imdb.domain.role.IsAdmin;
import com.randered.imdb.domain.user.entity.User;
import com.randered.imdb.domain.user.service.UserService;
import com.randered.imdb.util.validation.ValidationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.randered.imdb.util.common.Constants.*;

@RestController
@RequestMapping(BASE_PATH + PATH_ADMIN)
@RequiredArgsConstructor
@Validated
@IsAdmin
public class AdminMovieController {

    private final MovieService movieService;
    private final ActorService actorService;
    private final UserService userService;
    private final RatingRepository ratingRepository;

    @PostMapping(IMAGE_MOVIE)
    public ResponseEntity<ValidationResponse> uploadImage(
            @NotNull @RequestParam MultipartFile image,
            @NotEmpty @RequestParam String movieName) {

        movieService.addImageToMovie(movieName, image);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ValidationResponse(SUCCESSFUL_IMAGE_UPLOAD, HttpStatus.OK));
    }

    @PostMapping(CREATE_MOVIE)
    public ResponseEntity<ValidationResponse> createMovie(@Valid @RequestBody MovieDto movieDto) {
        movieService.createMovie(movieDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ValidationResponse(MOVIE_CREATED, HttpStatus.OK));
    }

    @PostMapping(CREATE_ACTOR)
    public ResponseEntity<ValidationResponse> createActor(@Valid @RequestBody ActorDto actorDto) {
        actorService.createActor(actorDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ValidationResponse("ACTOR_CREATED", HttpStatus.OK));
    }

    @GetMapping("/actors")
    public ResponseEntity<List<ActorDto>> getAllActors() {
        return ResponseEntity.ok().body(actorService.findAll());
    }

    @PutMapping
    public ResponseEntity<ValidationResponse> updateMovie(@Valid @RequestBody MovieDto moviedto) {
        movieService.updateMovie(moviedto);
        return ResponseEntity.status(HttpStatus.OK).body(new ValidationResponse("MOVIE_UPDATED"));
    }

    @DeleteMapping(DELETE_MOVIE)
    public ResponseEntity<ValidationResponse> deleteMovie(
            @NotNull(message = "INVALID_MOVIE_ID") @RequestParam Integer id) {

        movieService.deleteMovie(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ValidationResponse(MOVIE_DELETED));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping(USER_RATINGS)
    public ResponseEntity<List<Rating>> getAllRatingsForUser(final String username) {
        final User user = userService.findUserByUsername(username);
        return ResponseEntity.ok().body(ratingRepository.findAllByUser(user));
    }
}
