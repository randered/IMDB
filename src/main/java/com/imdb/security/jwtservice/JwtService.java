package com.imdb.security.jwtservice;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdb.domain.user.service.UserService;
import com.imdb.util.common.Constants;
import com.imdb.domain.user.entity.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class JwtService {
    private static final Algorithm algorithm = Algorithm.HMAC512(Constants.SECRET.getBytes());

    public static DecodedJWT decodeToken(@NonNull final String token) {
        final Verification verifier = JWT.require(algorithm);
        return verifier.build().verify(token);
    }

    public static String getSubjectUsername(@NonNull final String token) {
        final DecodedJWT decodedJWT = decodeToken(token);
        return decodedJWT.getSubject();
    }

    public static String[] getSubjectRoles(@NonNull final String token) {
        final DecodedJWT decodedJWT = decodeToken(token);
        return decodedJWT.getClaim(Constants.ROLE).asArray(String.class);
    }

    public static String createLoginTokenUserDetails(@NonNull final UserDetails authenticatedUser) {
        return JWT.create()
                .withSubject(authenticatedUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
                .withClaim(Constants.ROLE, authenticatedUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public static String createRefreshToken(@NonNull final UserDetails authenticatedUserDetails) {
        return JWT.create()
                .withSubject(authenticatedUserDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.REFRESH_EXPIRATION_TIME))
                .sign(algorithm);
    }

    public static String createLoginTokenUserEntity(@NonNull final User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
                .withClaim(Constants.ROLE, List.of(user.getRole()))
                .sign(algorithm);
    }

    public static void refreshToken(final String authorizationHeader, final HttpServletResponse res, final UserService userService) throws IOException {
        final String refresh_token = authorizationHeader.substring(Constants.TOKEN_PREFIX.length());
        final String username = JwtService.getSubjectUsername(refresh_token);
        final User user = userService.findUserByUsername(username);

        final String login_token = JwtService.createLoginTokenUserEntity(user);

        final Map<String, String> tokens = new HashMap<>();
        tokens.put(HttpHeaders.AUTHORIZATION, login_token);
        tokens.put(HttpHeaders.EXPIRES, refresh_token);
        res.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(res.getOutputStream(), tokens);
    }
}
