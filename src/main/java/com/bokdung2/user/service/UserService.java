package com.bokdung2.user.service;

import com.bokdung2.user.dto.response.LoginTokenRes;
import org.springframework.stereotype.Component;

import java.util.HashMap;

public interface UserService {
  LoginTokenRes kakaoLogin(String code);

  LoginTokenRes signIn(HashMap<String, String> userInfo);

  void logout(Long userIdx);

  boolean checkIsUserExists(long userIdx);
}
