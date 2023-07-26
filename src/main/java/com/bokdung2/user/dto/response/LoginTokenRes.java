package com.bokdung2.user.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class LoginTokenRes {

  private String access_token;
  private String refresh_token;

  @Builder
  public LoginTokenRes(String access_token, String refresh_token) {
    this.access_token = access_token;
    this.refresh_token = refresh_token;
  }

  public static LoginTokenRes toDto(String token) {
    return LoginTokenRes.builder()
            .access_token(token.split(",")[0])
            .refresh_token(token.split(",")[1])
            .build();
  }
}
