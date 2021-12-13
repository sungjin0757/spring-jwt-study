package study.jwt.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.jwt.exception.DuplicateEmailException;
import study.jwt.exception.ErrorResult;
import study.jwt.exception.JwtException;
import study.jwt.exception.LoginFailureException;

@RestControllerAdvice
@Slf4j
public class ExControllerAdvice {

    @ExceptionHandler(LoginFailureException.class)
    public ResponseEntity<ErrorResult> loginFail(LoginFailureException e){
        return new ResponseEntity<>(new ErrorResult("BAD",e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResult> duplicate(DuplicateEmailException e){
        return new ResponseEntity<>(new ErrorResult("DUPLICATE", e.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResult notValidateJwt(JwtException e){
        return new ErrorResult("NOT_VALIDATE",e.getMessage());
    }
}
