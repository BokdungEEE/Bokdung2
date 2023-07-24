package com.bokdung2.user.service;

import com.bokdung2.user.dto.KakaoLogin;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class KakaoServiceImpl implements KakaoService {

  @Value("${kakao.client_id}")
  private String client_id;

  @Value("${kakao.redirect_uri}")
  private String redirect_uri;

  @Override
  public String getAuthorizationUrl(HttpSession session) {
    OAuth20Service oauthService = new ServiceBuilder()
            .apiKey(client_id)
            .callback(redirect_uri)
            .build(KakaoLogin.instance());
    return oauthService.getAuthorizationUrl();
  }

  @Override
  public String getAccessToken(String authorize_code) {
    String access_Token = "";
    String refresh_Token = "";
    String reqURL = "https://kauth.kakao.com/oauth/token";

    try {
      URL url = new URL(reqURL);

      HttpURLConnection conn = (HttpURLConnection) url.openConnection();

      conn.setRequestMethod("POST");
      conn.setDoOutput(true);

      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
      StringBuilder sb = new StringBuilder();
      sb.append("grant_type=authorization_code");


      sb.append("&client_id=" + client_id);
      sb.append("&redirect_uri=" + redirect_uri);

      sb.append("&code=" + authorize_code);
      bw.write(sb.toString());
      bw.flush();

      BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line = "";
      String result = "";

      while ((line = br.readLine()) != null) {
        result += line;
      }

      JsonParser parser = new JsonParser();
      JsonElement element = parser.parse(result);

      access_Token = element.getAsJsonObject().get("access_token").getAsString();
      refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

      br.close();
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return access_Token;
  }

  @Override
  public HashMap<String, String> getUserInfo(String access_Token) {
    HashMap<String, String> userInfo = new HashMap<String, String>();
    String reqURL = "https://kapi.kakao.com/v2/user/me";
    try {
      URL url = new URL(reqURL);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");

      conn.setRequestProperty("Authorization", "Bearer " + access_Token);

      int responseCode = conn.getResponseCode();

      BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

      String line = "";
      String result = "";

      while ((line = br.readLine()) != null) {
        result += line;
      }
      JsonParser parser = new JsonParser();
      JsonElement element = parser.parse(result);

      JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
      String email = kakao_account.getAsJsonObject().get("email").getAsString();
      JsonObject kakao_nickname = element.getAsJsonObject().get("properties").getAsJsonObject();
      String nickname = kakao_nickname.getAsJsonObject().get("nickname").getAsString();
      String id = element.getAsJsonObject().get("id").getAsString();

      userInfo.put("email", email);
      userInfo.put("nickname", nickname);
      userInfo.put("id", id);

      return userInfo;

    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
