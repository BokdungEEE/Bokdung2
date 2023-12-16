package com.bokdung2.user.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
public class LoginTokenRes {

  private String access_token;
  private String refresh_token;
  private UUID uuid;

  @Builder
  public LoginTokenRes(String access_token, String refresh_token, UUID uuid) {
    this.access_token = access_token;
    this.refresh_token = refresh_token;
    this.uuid = uuid;
  }

  public static LoginTokenRes toDto(String token) {
    return LoginTokenRes.builder()
            .access_token(token.split(",")[0])
            .refresh_token(token.split(",")[1])
            .build();
  }
}
