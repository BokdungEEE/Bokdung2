package com.bokdung2.user.controller;

import com.bokdung2.global.dto.ResponseCustom;
import com.bokdung2.global.resolver.Auth;
import com.bokdung2.global.resolver.IsLogin;
import com.bokdung2.global.resolver.LoginStatus;
import com.bokdung2.user.dto.response.LoginTokenRes;
import com.bokdung2.user.service.KakaoService;
import com.bokdung2.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  private final KakaoService kakaoService;

  // 카카오 로그인 url 요청
  @GetMapping("/login/kakao")
  public ResponseCustom<?> kakaoLogin(HttpSession session) {
    String httpHeaders = kakaoService.getAuthorizationUrl(session);
    return ResponseCustom.OK(httpHeaders);
  }

  // 카카오 로그인 콜백
  @ResponseBody
  @GetMapping("/callback/kakao")
  public ResponseCustom<LoginTokenRes> kakaoCallback(@RequestParam String code) {
    return ResponseCustom.OK(userService.kakaoLogin(code));
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
