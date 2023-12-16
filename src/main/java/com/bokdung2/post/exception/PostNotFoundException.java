package com.bokdung2.post.exception;

public class PostNotFoundException extends RuntimeException {
  public PostNotFoundException() {
    super("idx에 일치하는 post가 존재하지 않습니다.");
  }
}
