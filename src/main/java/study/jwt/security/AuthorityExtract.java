package study.jwt.security;

import org.springframework.security.core.GrantedAuthority;
import study.jwt.domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthorityExtract {

    public static Collection<GrantedAuthority> getAuthorities(User user){
        Collection<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole().getValue();
            }
        });
        return authorities;
    }
}
