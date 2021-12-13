package study.jwt.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import study.jwt.domain.User;
import study.jwt.domain.dto.UserDto;
import study.jwt.domain.dto.UserLoginDto;


public interface UserService {
    Long signup(UserDto userDto);
    User login(UserLoginDto userLoginDto);
    User getUserByEmail(String email);
}
