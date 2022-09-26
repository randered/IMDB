package com.randered.imdb.domain.user.authentication.authority;

import org.springframework.security.core.GrantedAuthority;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Authority implements GrantedAuthority {

    ADMIN,
    USER;

    private static final Map<Authority, Set<Authority>> GRANTED_AUTHORITIES;

    static {
        final Authority lastRegularAuthority = Authority.USER;

        GRANTED_AUTHORITIES = Stream
                .of(Authority.values())
                .filter(authority -> authority.ordinal() <= lastRegularAuthority.ordinal())
                .collect(Collectors.toMap(
                        k -> k,
                        k -> EnumSet.range(k, lastRegularAuthority),
                        (l, r) -> {
                            throw new IllegalArgumentException("Duplicate keys " + l + "and " + r + ".");
                        },
                        () -> new EnumMap<>(Authority.class)
                ));

        Stream.of(Authority.values())
                .filter(authority -> authority.ordinal() > lastRegularAuthority.ordinal())
                .forEach(authority -> GRANTED_AUTHORITIES.put(authority, Set.of()));
    }

    @Override
    public String getAuthority() {
        return this.name();
    }

    public Set<Authority> getGrantedAuthorities() {
        return GRANTED_AUTHORITIES.get(this);
    }
}
