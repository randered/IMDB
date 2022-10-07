package com.randered.imdb.web;

import com.randered.imdb.domain.actor.actorDTO.ActorDto;
import com.randered.imdb.domain.actor.service.ActorService;
import com.randered.imdb.domain.movie.movieDTO.MovieDto;
import com.randered.imdb.domain.movie.service.MovieService;
import com.randered.imdb.domain.role.IsAdmin;
import com.randered.imdb.domain.user.entity.User;
import com.randered.imdb.domain.user.service.UserService;
import com.randered.imdb.util.validation.ValidationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
@IsAdmin
public class AdminMovieController {

    private final MovieService movieService;
    private final ActorService actorService;

    private final UserService userService;

//    @PostMapping(IMAGE_PATH)
//    public ResponseEntity<ValidationResponse> uploadImage(
//            @NotNull(message = NULL_MULTIPART_FILE) @RequestParam MultipartFile image,
//            @NotEmpty(message = EMPTY_MOVIE_NAME) @RequestParam String movieName) {
//
//        movieService.uploadImage(movieName, image);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(new ValidationResponse(IMAGE_UPLOADED, HttpStatus.OK));
//    }
//
//    @DeleteMapping(IMAGE_PATH)
//    public ResponseEntity<ValidationResponse> deleteImage(
//            @NotEmpty(message = EMPTY_MOVIE_NAME) @RequestParam String movieName){
//
//        movieService.deleteImage(movieName);
//        return ResponseEntity.status(HttpStatus.OK).body(new ValidationResponse(IMAGE_DELETED));
//    }

    @PostMapping("/movie/create")
    public ResponseEntity<ValidationResponse> createMovie(@Valid @RequestBody MovieDto movieDto) {
        movieService.createMovie(movieDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ValidationResponse("MOVIE_CREATED", HttpStatus.OK));
    }

    @PostMapping("/actor/create")
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

    @DeleteMapping
    public ResponseEntity<ValidationResponse> deleteMovie(
            @NotNull(message = "INVALID_MOVIE_ID") @RequestParam Integer id) {

        movieService.deleteMovie(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ValidationResponse("MOVIE_DELETED"));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }
}
