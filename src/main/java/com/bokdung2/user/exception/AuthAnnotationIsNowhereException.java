package com.bokdung2.user.exception;

public class AuthAnnotationIsNowhereException extends RuntimeException {
  public AuthAnnotationIsNowhereException() {
    super("토큰을 통해 userId를 추출하는 메서드에는 @Auth 어노테이션을 붙여주세요.");
  }
}
