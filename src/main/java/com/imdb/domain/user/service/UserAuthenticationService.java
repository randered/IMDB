package com.imdb.domain.user.service;

import com.imdb.domain.role.Role;
import com.imdb.domain.user.dto.UserDto;
import com.imdb.domain.user.entity.User;
import com.imdb.domain.user.mapper.UserMapper;
import com.imdb.exceptions.UserAlreadyExistAuthenticationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

import static com.imdb.util.common.Constants.USER_NOT_FOUND_MSG;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserAuthenticationService implements UserDetailsService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(@NonNull final UserDto userDto) {
        User user = userService.findUserByUsername(userDto.getUsername());
        if (null != user) {
            log.error("User Already exist!");
            throw new UserAlreadyExistAuthenticationException(userDto.getUsername());
        }
        user = mapUser(userDto);
        userMapper.map(userService.update(user));
        log.info("User Created {} ", user.getFullName());
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = userService.findUserByUsername(username);
        if (user == null) {
            log.error(String.format(USER_NOT_FOUND_MSG, username));
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username));
        }
        final Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        log.info("User authorized {} ", user.getFullName());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    private User mapUser(final UserDto userDto) {
        User user = userMapper.map(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);
        if (userService.count() == 0L) {
            user.setRole(Role.ADMIN);
        }
        return user;
    }

    public User getContextHolder() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userService.findUserById(Integer.valueOf(jwt.getId())).orElseThrow(RuntimeException::new);
    }
}
