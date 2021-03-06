package study.jwt.domain.dto;

import lombok.Builder;
import lombok.Data;
import study.jwt.domain.value.Role;

@Data
public class UserDto {
    private String email;
    private String password;
    private Role role;

    @Builder(builderMethodName = "createUserDto")
    public UserDto(String email,String password,Role role){
        this.email=email;
        this.password=password;
        this.role=role;
    }
}
