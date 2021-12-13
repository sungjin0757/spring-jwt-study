package study.jwt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import study.jwt.domain.User;
import study.jwt.domain.dto.UserDto;
import study.jwt.domain.dto.UserLoginDto;
import study.jwt.exception.DuplicateEmailException;
import study.jwt.exception.JwtException;
import study.jwt.security.AuthorityExtract;
import study.jwt.security.JwtProvider;
import study.jwt.service.UserService;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto userDto)  {
        userService.signup(userDto);

        return new ResponseEntity<>("Login Success",HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto) {
        User loginUser = userService.login(userLoginDto);

        String token = JwtProvider.generateToken(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), null,
                AuthorityExtract.getAuthorities(loginUser)));

        return new ResponseEntity<>(token,HttpStatus.OK);
    }

    @GetMapping("/test")
    public UserDto getUser(@AuthenticationPrincipal String username){
        User findUser = userService.getUserByEmail(username);

        return UserDto.createUserDto()
                .email(findUser.getEmail())
                .password(findUser.getPassword())
                .build();
    }
}
