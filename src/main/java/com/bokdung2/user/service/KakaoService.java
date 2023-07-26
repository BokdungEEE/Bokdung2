package com.bokdung2.user.service;


import javax.servlet.http.HttpSession;
import java.util.HashMap;

public interface KakaoService {

  public String getAuthorizationUrl(HttpSession session);

  public String getAccessToken(String authorize_code);

  public HashMap<String, String> getUserInfo(String access_Token);
}
