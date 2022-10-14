package com.imdb.domain.user.mapper;

import com.imdb.domain.user.entity.User;
import com.imdb.domain.user.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto map(User user);

    User map(UserDto userDto);
}
