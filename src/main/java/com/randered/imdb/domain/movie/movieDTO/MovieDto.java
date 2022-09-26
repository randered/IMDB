package com.randered.imdb.domain.movie.movieDTO;

import com.randered.imdb.domain.actor.actorDTO.ActorDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class MovieDto {

    @NotEmpty(message = "Movie name must not be empty.")
    private String name;

    @NotNull(message = "Year must not be null.")
    private int year;

    @NotEmpty(message = "Actors list must not be empty.")
    private Set<@Valid ActorDto> actors;

    @NotEmpty(message = "Genre must not be empty.")
    private String genre;

    @NotBlank(message = "Trailer must not be blank.")
    private String trailer;

    private double averageRating;

    public MovieDto() {
        this.actors = new HashSet<>();
    }
}
