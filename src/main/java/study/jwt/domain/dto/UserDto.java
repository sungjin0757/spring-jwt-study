package study.jwt.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String password;

    @Builder(builderMethodName = "createUserDto")
    public UserDto(String email,String password){
        this.email=email;
        this.password=password;
    }
}
