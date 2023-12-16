package com.bokdung2.post.dto.response;

import com.bokdung2.post.entity.Post;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetailPostResponse {

  private String message;
  private String senderNickname;

  public static DetailPostResponse toDto(Post post) {
    return DetailPostResponse.builder()
            .message(post.getMessage())
            .senderNickname(post.getSenderNickname())
            .build();
  }
}
