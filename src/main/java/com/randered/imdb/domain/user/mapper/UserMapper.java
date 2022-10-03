package com.randered.imdb.domain.user.mapper;

import com.randered.imdb.domain.user.entity.User;
import com.randered.imdb.domain.user.userDTO.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto map(User user);

    User map(UserDto userDto);
}
