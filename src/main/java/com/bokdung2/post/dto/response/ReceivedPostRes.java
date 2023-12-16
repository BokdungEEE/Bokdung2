package com.bokdung2.post.dto.response;

import com.bokdung2.post.entity.Post;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceivedPostRes {
  private Long postIdx;
  private String cardImgKey_f;
  private String senderNickname;

  public static ReceivedPostRes toDto(Post post) {
    return ReceivedPostRes.builder()
            .postIdx(post.getPostIdx())
            .cardImgKey_f(post.getCard().getCardImgKey_f())
            .senderNickname(post.getSenderNickname())
            .build();
  }
}
