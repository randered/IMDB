package com.randered.imdb.domain.user.controllers;

import com.randered.imdb.domain.user.entity.User;
import com.randered.imdb.domain.user.service.UserAuthenticationService;
import com.randered.imdb.domain.user.userDTO.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.randered.imdb.util.common.Constants.REGISTER;
import static com.randered.imdb.util.common.Constants.USER_URL;

@Validated
@RestController
@RequestMapping(USER_URL)
@RequiredArgsConstructor
public class UserController {

    private final UserAuthenticationService userAuthenticationService;

    @PostMapping(REGISTER)
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.created(null).body(userAuthenticationService.registerUser(userDto));
    }

    @GetMapping("/")
    String home() {
        return "home";
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok().body(userAuthenticationService.findAll());
    }

}
