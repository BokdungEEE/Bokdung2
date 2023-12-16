package com.bokdung2.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class LoginTokenRes {

  private String access_token;
  private String refresh_token;
  private Long userIdx;

  @Builder
  public LoginTokenRes(String access_token, String refresh_token, Long userIdx) {
    this.access_token = access_token;
    this.refresh_token = refresh_token;
    this.userIdx = userIdx;
  }

  public static LoginTokenRes toDto(String token) {
    return LoginTokenRes.builder()
            .access_token(token.split(",")[0])
            .refresh_token(token.split(",")[1])
            .build();
  }
}
