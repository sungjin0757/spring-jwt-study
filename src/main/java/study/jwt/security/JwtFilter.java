package study.jwt.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import study.jwt.domain.User;
import study.jwt.domain.value.Role;
import study.jwt.exception.JwtException;
import study.jwt.service.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try{
            String jwt=getJwt(request);
            if(StringUtils.hasText(jwt) && JwtProvider.validateJwt(jwt)){
                String userName = JwtProvider.getUsernameFromJwt(jwt);
                User findUser = userService.getUserByEmail(userName);

                Authentication authentication= new UsernamePasswordAuthenticationToken(userName,
                        null,
                        AuthorityExtract.getAuthorities(findUser));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else if(!StringUtils.hasText(jwt)){
                request.setAttribute("UnAuthorized","Empty Token");
            }else if(!JwtProvider.validateJwt(jwt)){
                request.setAttribute("unAuthorized","Expire Token");
            }
        }catch (Exception e){
            log.error("Exception Occurred : {}",e.getMessage());
//            throw new JwtException(e.getMessage(),e.getCause());
        }
        filterChain.doFilter(request,response);
    }

    private String getJwt(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        log.info("Token : {}",bearerToken);
        if(bearerToken.startsWith("Bearer ")){
            return bearerToken.replace("Bearer ","");
        }
        return null;
    }
}
