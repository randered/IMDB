package com.randered.imdb.domain.movie.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieFilter {

    private String name;

    private Integer year;

    private String genre;
}
