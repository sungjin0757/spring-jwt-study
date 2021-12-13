package study.jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.jwt.domain.User;
import study.jwt.domain.dto.UserDto;
import study.jwt.domain.value.Role;
import study.jwt.exception.DuplicateEmailException;
import study.jwt.repository.UserRepository;
import study.jwt.security.PrincipalDetails;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long signup(UserDto userDto) {
        if(validateUser(userDto.getEmail())){
            throw new DuplicateEmailException("Duplicate Email!");
        }
        User user=mappingUser(userDto.getEmail(), userDto.getPassword());
        userRepository.save(user);

        return user.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).map(u->new PrincipalDetails(u))
                .orElseThrow(()->{
                    throw new UsernameNotFoundException("Not Found");
                });
    }

    private User mappingUser(String email,String password){
        return User.createUser()
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.ROLE_USER)
                .build();
    }

    private boolean validateUser(String email){
        if(userRepository.findByEmail(email).isEmpty())
            return false;
        return true;
    }
}
