package com.bokdung2.user.dto.assembler;

import com.bokdung2.user.entity.Provider;
import com.bokdung2.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;

@Component
public class UserAssembler {
  public User toEntity(HashMap<String, String> userInfo) {
    return User.builder()
            .kakaoId(Long.valueOf(userInfo.get("id")))
            .email(userInfo.get("email"))
            .username(userInfo.get("nickname"))
            .provider(Provider.KAKAO)
            .uuid(UUID.randomUUID())
            .build();
  }
}
