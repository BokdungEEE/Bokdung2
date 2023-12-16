package com.bokdung2.user.service;

import com.bokdung2.user.dto.response.LoginTokenRes;

import java.util.HashMap;

public interface UserService {
  LoginTokenRes kakaoLogin(String code);

  LoginTokenRes signIn(HashMap<String, String> userInfo);

  void logout(Long userIdx);

  boolean checkUserExists(String uuid);

  String getUserName(String uuid);
  String getUserName(long Idx);

  long getChances(Long userIdx);
}
