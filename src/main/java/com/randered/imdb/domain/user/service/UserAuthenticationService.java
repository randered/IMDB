package com.randered.imdb.domain.user.service;

import com.randered.imdb.customExceptions.UserAlreadyExistAuthenticationException;
import com.randered.imdb.domain.authority.Role;
import com.randered.imdb.domain.user.entity.User;
import com.randered.imdb.domain.user.userDTO.UserDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.randered.imdb.util.common.Constants.USER_NOT_FOUND_MSG;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService implements UserDetailsService {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDto registerUser(@NonNull final UserDto userDto) {
        User user = userService.findUserByUsername(userDto.getUsername());
        if (null != user) {
            throw new UserAlreadyExistAuthenticationException(userDto.getUsername());
        }
        user = mapUser(userDto);
        return modelMapper.map(userService.update(user), UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userService.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username));
        }
        final Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    private User mapUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);

        if (userService.count() == 0L) {
            user.setRole(Role.ADMIN);
        }
        return user;
    }

    public List<User> findAll() {
        return userService.findAll();
    }
}
