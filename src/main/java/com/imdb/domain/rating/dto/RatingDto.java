package com.imdb.domain.rating.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter
@Setter
public class RatingDto {

    @NotEmpty()
    @Size(max = 100)
    private String movieName;

    @Min(value = 1)
    @Max(value = 10)
    private double rating;

    private String comment;
}
