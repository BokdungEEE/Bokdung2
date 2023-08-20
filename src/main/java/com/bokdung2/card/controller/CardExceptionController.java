package com.bokdung2.card.controller;

import com.bokdung2.card.exception.CardNotFoundException;
import com.bokdung2.global.dto.ResponseCustom;
import com.bokdung2.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CardExceptionController {

  @ExceptionHandler(CardNotFoundException.class)
  public ResponseCustom<?> catchCardNotFoundException(CardNotFoundException e) {
    log.error(e.getMessage());
    return ResponseCustom.NOT_FOUND(e.getMessage());
  }
}
