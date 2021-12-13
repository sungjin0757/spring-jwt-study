package study.jwt.security;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;

public class JwtProviderTest {
    Authentication authentication;

    @BeforeEach
    void setUp(){
        authentication=new UsernamePasswordAuthenticationToken("1","2",new ArrayList<>());
    }

    @Test
    @DisplayName("JWT Test")
    void 발행_인증_테스트(){
        String token = JwtProvider.generateToken(authentication);

        Assertions.assertThat(JwtProvider.getUsernameFromJwt(token)).isEqualTo("1");
        Assertions.assertThat(JwtProvider.validateJwt(token)).isEqualTo(true);
        org.junit.jupiter.api.Assertions.assertThrows(Exception.class,()->{
            JwtProvider.validateJwt("test");
        });
    }
}
