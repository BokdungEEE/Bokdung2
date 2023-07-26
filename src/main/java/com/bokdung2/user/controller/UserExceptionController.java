package com.bokdung2.user.controller;


import com.bokdung2.global.dto.ResponseCustom;
import com.bokdung2.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExceptionController {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseCustom<?> catchUserNotFoundException(UserNotFoundException e) {
    log.error(e.getMessage());
    return ResponseCustom.NOT_FOUND(e.getMessage());
  }
}
