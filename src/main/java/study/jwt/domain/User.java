package study.jwt.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.jwt.domain.value.Role;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
@Getter
public class User {

    @Column(name = "user_id")
    @Id @GeneratedValue
    private Long id;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder(builderMethodName = "createUser")
    public User(String email,String password,Role role){
        this.email=email;
        this.password=password;
        this.role=role;
    }
}
