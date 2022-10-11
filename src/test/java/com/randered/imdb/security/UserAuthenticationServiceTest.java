package com.randered.imdb.security;

import com.randered.imdb.base.BaseTest;
import com.randered.imdb.domain.user.entity.User;
import com.randered.imdb.domain.user.repository.UserRepository;
import com.randered.imdb.domain.user.service.UserAuthenticationService;
import com.randered.imdb.domain.user.userDTO.UserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

public class UserAuthenticationServiceTest extends BaseTest {

    @MockBean
    protected UserRepository userRepository;
    @Autowired
    private UserAuthenticationService authenticationService;

    @Test
    void testRegisterUser() {
        UserDto userDto = setUserDto();
        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());

        authenticationService.registerUser(userDto);

        Mockito.verify(userRepository).findByUsername(usernameArgumentCaptor.capture());
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());

        final User user = userArgumentCaptor.getValue();
        final String username = usernameArgumentCaptor.getValue();

        Assertions.assertThat(username).isEqualTo(userDto.getUsername());
        Assertions.assertThat(userDto.getUsername()).isEqualTo(user.getUsername());
    }

    private UserDto setUserDto() {
        final UserDto userDto = new UserDto();
        userDto.setUsername(buildUser().getUsername());
        userDto.setPassword(buildUser().getPassword());
        return userDto;
    }
}
