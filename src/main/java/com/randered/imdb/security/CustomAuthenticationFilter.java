package com.randered.imdb.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.randered.imdb.security.jwtservice.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest req,
                                                final HttpServletResponse res) throws AuthenticationException {
        final String username = req.getParameter("username");
        final String password = req.getParameter("password");
        log.info("Username is: {} ", username);
        log.info("Password is: {} ", password);

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password)
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {

        User user = (User) auth.getPrincipal();

        final String login_token = JwtService.createLoginTokenUserDetails(user);

        final String refresh_token = JwtService.createRefreshToken(user);

        res.setHeader(HttpHeaders.AUTHORIZATION, login_token);
        res.setHeader(HttpHeaders.EXPIRES, refresh_token);

        final Map<String, String> tokens = new HashMap<>();
        tokens.put(HttpHeaders.AUTHORIZATION, login_token);
        tokens.put(HttpHeaders.EXPIRES, refresh_token);
        res.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(res.getOutputStream(), tokens);
    }
}
