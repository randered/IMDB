package com.randered.imdb.security;

import com.randered.imdb.domain.user.authentication.authority.Authority;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;

@Setter
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AuthenticatedUserDetails implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private final Integer id;

    @ToString.Exclude
    private final String username;
    private final String password;
    private final Authority authority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority.getGrantedAuthorities();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
