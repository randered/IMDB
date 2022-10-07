package com.randered.imdb.domain.actor.mapper;

import com.randered.imdb.domain.actor.actorDTO.ActorDto;
import com.randered.imdb.domain.actor.entity.Actor;
import com.randered.imdb.domain.movie.mapper.MovieMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MovieMapper.class})
public interface ActorMapper {
    ActorDto map(Actor actor);

    Actor map(ActorDto actorDto);
}
