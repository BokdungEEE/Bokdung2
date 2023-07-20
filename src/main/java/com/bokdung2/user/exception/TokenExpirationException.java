package com.bokdung2.user.exception;

public class TokenExpirationException extends RuntimeException {
  public TokenExpirationException() {
    super("만료된 토큰입니다. 다시 발급해주세요.");
  }
}
