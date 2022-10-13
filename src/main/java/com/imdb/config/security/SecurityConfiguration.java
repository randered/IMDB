package com.imdb.config.security;

import com.imdb.domain.user.service.UserAuthenticationService;
import com.imdb.security.CustomAuthorizationFilter;
import com.imdb.util.common.Constants;
import com.imdb.security.CustomAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    private final UserAuthenticationService userAuthenticationService;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        final AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userAuthenticationService);
        final AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        final CustomAuthenticationFilter authenticationFilter = new CustomAuthenticationFilter(authenticationManager);
        authenticationFilter.setFilterProcessesUrl(Constants.LOGIN);

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(Constants.LOGIN + Constants.ALLOW_ALL, Constants.BASE_PATH + Constants.TOKEN_REFRESH,
                        Constants.BASE_PATH + Constants.REGISTER, Constants.LIST_MOVIES)
                .permitAll();

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/user/**").hasAnyAuthority(Constants.USER);

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/admin/**").hasAnyAuthority(Constants.ADMIN);
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/admin/**").hasAnyAuthority(Constants.ADMIN);
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/admin/**").hasAnyAuthority(Constants.ADMIN);
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/admin/**").hasAnyAuthority(Constants.ADMIN);

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authenticationManager(authenticationManager);
        return http.build();
    }
}
