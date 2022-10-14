package com.imdb.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imdb.security.jwtservice.JwtService;
import com.imdb.util.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        if (allowedPaths(request)) {
            filterChain.doFilter(request, response);
        } else {
            final String authorizationHeader = request.getHeader(Constants.HEADER_STRING);
            if (authorizationHeader != null && authorizationHeader.startsWith(Constants.TOKEN_PREFIX)) {
                try {
                    final String token = authorizationHeader.substring(Constants.TOKEN_PREFIX.length());

                    final String username = JwtService.getSubjectUsername(token);
                    final String[] roles = JwtService.getSubjectRoles(token);

                    final Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });

                    final UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                    filterChain.doFilter(request, response);
                } catch (Exception exception) {
                    log.error("Error logging in: {}", exception.getMessage());
                    response.setHeader("error", exception.getMessage());
                    response.setStatus(FORBIDDEN.value());

                    final Map<String, String> error = new HashMap<>();
                    error.put("error_message", exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    private boolean allowedPaths(HttpServletRequest request) {
        return request.getServletPath().equals(Constants.BASE_PATH + Constants.LOGIN) ||
               request.getServletPath().equals(Constants.BASE_PATH + Constants.MOVIES) ||
               request.getServletPath().equals(Constants.BASE_PATH + Constants.REGISTER) ||
               request.getServletPath().equals(Constants.BASE_PATH + Constants.TOKEN_REFRESH);
    }
}

