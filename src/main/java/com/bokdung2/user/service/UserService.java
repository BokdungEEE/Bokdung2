package com.bokdung2.user.service;

import com.bokdung2.user.dto.response.LoginTokenRes;
import org.springframework.stereotype.Component;

public interface UserService {
  LoginTokenRes kakaoLogin(String code);

  void logout(Long userIdx);
}
