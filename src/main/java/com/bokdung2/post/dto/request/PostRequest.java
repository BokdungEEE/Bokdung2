package com.bokdung2.post.dto.request;

import lombok.Getter;

@Getter
public class PostRequest {
  private Long cardIdx;
  private String nickname;
  private String message;
}
