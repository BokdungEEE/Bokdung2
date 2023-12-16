package com.bokdung2.user.controller;

import com.bokdung2.global.dto.ResponseCustom;
import com.bokdung2.global.resolver.Auth;
import com.bokdung2.global.resolver.IsLogin;
import com.bokdung2.global.resolver.LoginStatus;
import com.bokdung2.user.dto.response.LoginTokenRes;
import com.bokdung2.user.service.KakaoService;
import com.bokdung2.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.Base64;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  private final KakaoService kakaoService;

  @Value("${kakao.front_redirect_uri}")
  private String front_redirect_uri;

  // 카카오 로그인 url 요청
  @GetMapping("/login/kakao")
  @ResponseBody
  public ResponseCustom<?> kakaoLogin(HttpSession session) {
    String httpHeaders = kakaoService.getAuthorizationUrl(session);
    return ResponseCustom.OK(httpHeaders);
  }

  // 카카오 로그인 콜백
  @ResponseBody
  @GetMapping("/callback/kakao")
  public ResponseEntity<?> kakaoCallback(@RequestParam String code) throws JsonProcessingException {
    LoginTokenRes tokenResponse = userService.kakaoLogin(code);
    String accessToken = tokenResponse.getAccess_token();
    String refreshToken = tokenResponse.getRefresh_token();

    Map<String, String> parameters = Map.of(
            "accessToken", accessToken,
            "refreshToken", refreshToken
    );
    String parameterJson = new ObjectMapper().writeValueAsString(parameters);
    String parameterBase64 = Base64.getEncoder().encodeToString(parameterJson.getBytes());

    String queryParameter = "?parameter=" + parameterBase64;
    String redirect_uri = this.front_redirect_uri + queryParameter;

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(URI.create(redirect_uri));

    return new ResponseEntity<>( headers, HttpStatus.MOVED_PERMANENTLY);
  }

  // test controller
  @ResponseBody
  @GetMapping("/test")
  public ResponseCustom<?> test() {
    return ResponseCustom.OK("test");
  }

  @Auth
  @ResponseBody
  @PostMapping("/logout")
  public ResponseCustom<Void> logout(@IsLogin LoginStatus loginStatus) {
    userService.logout(loginStatus.getUserIdx());
    return ResponseCustom.OK();
  }

}
