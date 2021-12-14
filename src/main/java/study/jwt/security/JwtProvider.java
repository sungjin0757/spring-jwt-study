package study.jwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class JwtProvider {

    public static String generateToken(Authentication auth){
        return Jwts.builder()
                .setSubject(String.valueOf(auth.getPrincipal()))
                .setIssuedAt(TokenConst.NOW)
                .setExpiration(TokenConst.EXPIRATION)
                .signWith(SignatureAlgorithm.HS512,TokenConst.SECRET)
                .compact();
    }

    public static String getUsernameFromJwt(String token){
        return getClaim(token)
                .getSubject();
    }

    public static boolean validateJwt(String token){
        try{
            Claims claim = getClaim(token);
            if(jwtIsExpiration(claim) || jwtIsEmpty(claim))
                return false;
            return true;
        }catch(Exception e){
            throw e;
        }
    }

    private static Claims getClaim(String token){
        return Jwts.parser()
                .setSigningKey(TokenConst.SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    private static boolean jwtIsExpiration(Claims claim){
        if(claim.getExpiration().getTime()<=0)
            return true;
        return false;
    }

    private static boolean jwtIsEmpty(Claims claim){
        if(claim.isEmpty() || claim==null)
            return true;
        return false;
    }

}
