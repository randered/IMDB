package com.randered.imdb.domain.user.authentication;


import com.randered.imdb.customExceptions.UserAlreadyExistAuthenticationException;
import com.randered.imdb.domain.user.authentication.authority.Authority;
import com.randered.imdb.domain.user.entity.User;
import com.randered.imdb.domain.user.repository.UserRepository;
import com.randered.imdb.domain.user.userDTO.UserDto;
import com.randered.imdb.security.AuthenticatedUserDetails;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService implements UserDetailsService {
    private final UserRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(final UserDto userDto) {
        User user = repository.findByUsername(userDto.getUsername())
                .orElse(null);

        if (null != user) {
            throw new UserAlreadyExistAuthenticationException(userDto.getUsername());
        }
        user = mapUser(userDto);

        repository.save(user);
    }

    public User getUserById(Integer id) {
        return null;
    }

    public User getContextUser() {
        return null;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    private User mapUser(final UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAuthority(Authority.USER);

        if (repository.count() == 0L) {
            user.setAuthority(Authority.ADMIN);
        }
        return user;
    }

    private AuthenticatedUserDetails mapToCustomerDetails(final User user){

        return new AuthenticatedUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getAuthority());
    }
}
