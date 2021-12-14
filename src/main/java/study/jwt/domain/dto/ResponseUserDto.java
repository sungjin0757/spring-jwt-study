package study.jwt.domain.dto;

import lombok.Builder;
import lombok.Data;
import study.jwt.domain.value.Role;

@Data
public class ResponseUserDto {
    private String email;
    private Role role;

    @Builder(builderMethodName = "createResponseUser")
    public ResponseUserDto(String email,Role role){
        this.email=email;
        this.role=role;
    }
}
