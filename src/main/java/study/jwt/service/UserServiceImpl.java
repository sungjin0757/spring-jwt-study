package study.jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.jwt.domain.User;
import study.jwt.domain.dto.UserDto;
import study.jwt.domain.dto.UserLoginDto;
import study.jwt.domain.value.Role;
import study.jwt.exception.DuplicateEmailException;
import study.jwt.exception.LoginFailureException;
import study.jwt.repository.UserRepository;

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
    public User login(UserLoginDto userLoginDto) {
        User findUser=getUserByEmail(userLoginDto.getEmail());
        if(findUser!=null){
            if(matchPassword(userLoginDto.getPassword(),findUser)){
                return findUser;
            }
        }
        throw new LoginFailureException("Not Exist User!");
    }

    @Override
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    private User mappingUser(String email,String password){
        return User.createUser()
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.ROLE_USER)
                .build();
    }

    private boolean validateUser(String email){
        if(getUserByEmail(email)==null)
            return false;
        return true;
    }

    private boolean matchPassword(String password,User user){
        if(passwordEncoder.matches(password,user.getPassword())){
            return true;
        }
        return false;
    }
}
