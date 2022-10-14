package com.imdb.domain.movie.dto;

import com.imdb.domain.actor.dto.ActorDto;
import com.imdb.domain.rating.entity.Rating;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class MovieDto {

    @NotEmpty(message = "Movie name must not be empty.")
    private String name;

    @NotNull(message = "Year must not be null.")
    private int year;

    private List<Rating> ratings;
    private Set<@Valid ActorDto> actors;

    @NotEmpty(message = "Genre must not be empty.")
    private String genre;

    private String image;

    @NotBlank(message = "Trailer must not be blank.")
    private String trailer;

    private double averageRating;

    public MovieDto() {
        this.ratings = new ArrayList<>();
        this.actors = new HashSet<>();
    }
}
