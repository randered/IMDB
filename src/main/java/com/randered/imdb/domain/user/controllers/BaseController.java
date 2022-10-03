package com.randered.imdb.domain.user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.randered.imdb.domain.user.service.UserAuthenticationService;
import com.randered.imdb.domain.user.service.UserService;
import com.randered.imdb.domain.user.userDTO.UserDto;
import com.randered.imdb.security.jwtservice.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.randered.imdb.util.common.Constants.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping(BASE_PATH)
@RequiredArgsConstructor
public class BaseController {

    private final UserAuthenticationService userAuthenticationService;

    private final UserService userService;

    @PostMapping(REGISTER)
    public ResponseEntity<String> register(@Valid @RequestBody UserDto userDto) {
        try {
            userAuthenticationService.registerUser(userDto);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.created(null).body("User Created!");
    }

    @GetMapping("/")
    String home() {
        return "home";
    }

    @GetMapping(TOKEN_REFRESH)
    public void refreshToken(HttpServletRequest req, HttpServletResponse res) throws IOException {
        final String authorizationHeader = req.getHeader(HEADER_STRING);
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            try {
                JwtService.refreshToken(authorizationHeader, res, userService);
            } catch (Exception exception) {
                res.setHeader("error", exception.getMessage());
                res.setStatus(FORBIDDEN.value());

                final Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                res.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(res.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

//    @PostMapping()
//    public Page<MovieDto> getFilteredMovies(@Valid @RequestBody RequestDto<FilterDto> filters){
//        return customQueryService.listFilteredMovies(filters);
//    }
}
