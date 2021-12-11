package study.jwt.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import study.jwt.domain.User;
import study.jwt.domain.dto.UserDto;

public interface UserService extends UserDetailsService {
    Long signup(UserDto userDto);
}
