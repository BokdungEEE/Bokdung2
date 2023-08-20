package com.bokdung2.post.dto.response;

import com.bokdung2.post.entity.Post;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class PostResponse {

  private Integer postCount;
  private List<ReceivedPostRes> receivedPostRes;

  public static PostResponse toDto(List<Post> posts) {
    return PostResponse.builder()
            .postCount(posts.size())
            .receivedPostRes(posts.stream().map(ReceivedPostRes::toDto).collect(Collectors.toList()))
            .build();
  }

}
