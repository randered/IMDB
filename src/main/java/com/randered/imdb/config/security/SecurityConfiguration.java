package com.randered.imdb.config.security;

import com.randered.imdb.domain.user.service.UserAuthenticationService;
import com.randered.imdb.security.CustomAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfiguration {

    private final UserAuthenticationService userAuthenticationService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userAuthenticationService).passwordEncoder(passwordEncoder);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.csrf().and().cors().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authenticationManager(authenticationManager);
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/user/**").hasAnyAuthority();
        http.authorizeRequests().anyRequest().permitAll();
//                .antMatchers(HttpMethod.POST, USER_URL + REGISTER, LOGIN)
//                .permitAll()
//                .antMatchers(ANT_MATCHERS)
//                .permitAll()
//                .antMatchers(HttpMethod.GET, LOGIN, "/users", "/**")
        http.addFilter(new CustomAuthenticationFilter(authenticationManager));
        return http.build();
    }
}
