package com.bokdung2.user.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException() {
    super("해당 idx를 가진 유저가 존재하지 않습니다.");
  }
}
