import com.randered.imdb.domain.user.entity.User;
import com.randered.imdb.domain.user.mapper.UserMapper;
import com.randered.imdb.domain.user.userDTO.UserDto;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;

@Generated(
        value = "org.mapstruct.ap.MappingProcessor",
        date = "2022-10-03T16:38:28+0300",
        comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 17.0.3.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto map(User user) {
        if (user == null) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setFullName(user.getFullName());
        userDto.setRole(user.getRole());

        return userDto;
    }

    @Override
    public User map(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username(userDto.getUsername());
        user.password(userDto.getPassword());
        user.fullName(userDto.getFullName());
        user.role(userDto.getRole());

        return user.build();
    }
}