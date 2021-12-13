package study.jwt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import study.jwt.domain.User;
import study.jwt.domain.dto.UserDto;
import study.jwt.domain.value.Role;
import study.jwt.exception.DuplicateEmailException;
import study.jwt.exception.LoginFailureException;
import study.jwt.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
@Slf4j
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    List<UserDto> dtos;

    @BeforeEach
    void setUp(){
        dtos = Arrays.asList(UserDto.createUserDto().email("1").password("2").build(),
                UserDto.createUserDto().email("2").password("4").build(),
                UserDto.createUserDto().email("3").password("5").build());
    }

    @Test
    @DisplayName("save Test")
    void 회원_저장_테스트(){
        for(UserDto dto: dtos){
            Long userId=userService.signup(dto);

            User findUser=userRepository.findById(userId).get();
            Assertions.assertThat(findUser.getEmail()).isEqualTo(dto.getEmail());
            Assertions.assertThat(findUser.getRole()).isEqualTo(Role.ROLE_USER);

            Assertions.assertThat(passwordEncoder.matches(dto.getPassword(), findUser.getPassword()))
                    .isEqualTo(true);
        }

        Assertions.assertThat(userRepository.count()).isEqualTo(3);
        org.junit.jupiter.api.Assertions.assertThrows(UsernameNotFoundException.class,()->{
           userService.loadUserByUsername("123");
        });
        org.junit.jupiter.api.Assertions.assertThrows(DuplicateEmailException.class,()->{
           userService.signup(dtos.get(0));
        });
    }


}
