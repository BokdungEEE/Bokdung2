package com.bokdung2.user.controller;

import com.bokdung2.global.dto.ResponseCustom;
import com.bokdung2.user.dto.response.UserLoginRes;
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
  public ResponseCustom<?> kakaoCallback(@RequestParam String code) {
    return ResponseCustom.OK(userService.kakaoLogin(code));
  }

  // test controller
  @ResponseBody
  @GetMapping("/test")
  public ResponseCustom<?> test() {
    return ResponseCustom.OK("test");
  }
}
