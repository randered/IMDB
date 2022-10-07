package com.randered.imdb.domain.movie.mapper;

import com.randered.imdb.domain.movie.entity.Movie;
import com.randered.imdb.domain.movie.movieDTO.MovieDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDto map(Movie movie);

    Movie map(MovieDto movieDto);
}
