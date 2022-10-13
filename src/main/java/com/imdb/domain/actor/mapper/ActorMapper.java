package com.imdb.domain.actor.mapper;

import com.imdb.domain.actor.dto.ActorDto;
import com.imdb.domain.actor.entity.Actor;
import com.imdb.domain.movie.mapper.MovieMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MovieMapper.class})
public interface ActorMapper {
    ActorDto map(Actor actor);
    Actor map(ActorDto actorDto);
}
