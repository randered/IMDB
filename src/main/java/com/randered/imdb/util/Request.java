package com.randered.imdb.util;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class Request<T> {

    @Valid
    private T filter;

    @Positive(message = "Page size must be positive value.")
    private int pageSize;

    @Positive(message = "Page size must be positive value.")
    private int pageNumber;
}