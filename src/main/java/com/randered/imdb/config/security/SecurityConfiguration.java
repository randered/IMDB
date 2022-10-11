package com.randered.imdb.config.security;

import com.randered.imdb.domain.user.service.UserAuthenticationService;
import com.randered.imdb.security.CustomAuthenticationFilter;
import com.randered.imdb.security.CustomAuthorizationFilter;
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

import static com.randered.imdb.util.common.Constants.*;

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
        authenticationFilter.setFilterProcessesUrl(LOGIN);

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(LOGIN + ALLOW_ALL, BASE_PATH + TOKEN_REFRESH,
                        BASE_PATH + REGISTER, LIST_MOVIES)
                .permitAll();

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/user/**").hasAnyAuthority(USER);

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/admin/**").hasAnyAuthority(ADMIN);
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/admin/**").hasAnyAuthority(ADMIN);
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/admin/**").hasAnyAuthority(ADMIN);
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/admin/**").hasAnyAuthority(ADMIN);

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(authenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.authenticationManager(authenticationManager);
        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers(ANT_MATCHERS);
//    }
}
