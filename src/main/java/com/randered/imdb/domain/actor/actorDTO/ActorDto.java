package com.randered.imdb.domain.actor.actorDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActorDto {

    @NotEmpty(message = "Actor name must not be empty.")
    private String name;
}
