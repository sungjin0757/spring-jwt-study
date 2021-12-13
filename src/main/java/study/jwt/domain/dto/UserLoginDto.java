package study.jwt.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserLoginDto {
    private String email;
    private String password;

    @Builder(builderMethodName = "createUserDto")
    public UserLoginDto(String email, String password){
        this.email=email;
        this.password=password;
    }
}
