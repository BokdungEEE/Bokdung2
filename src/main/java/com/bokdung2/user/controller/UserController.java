package com.bokdung2.user.controller;

import com.bokdung2.global.dto.ResponseCustom;
import com.bokdung2.global.resolver.Auth;
import com.bokdung2.global.resolver.IsLogin;
import com.bokdung2.global.resolver.LoginStatus;
import com.bokdung2.post.service.PostService;
import com.bokdung2.user.dto.response.GetCountRes;
import com.bokdung2.user.dto.response.LoginTokenRes;
import com.bokdung2.user.service.KakaoService;
import com.bokdung2.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.Base64;
import java.util.Map;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;
  private final KakaoService kakaoService;
  private final PostService postService;

  @Value("${kakao.front_redirect_uri}")
  private String redirectUri;

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
    LoginTokenRes loginResponse = userService.kakaoLogin(code);

    Map<String, String> parameters = Map.of(
            "accessToken", loginResponse.getAccess_token(),
            "refreshToken", loginResponse.getRefresh_token(),
            "uuid", loginResponse.getUuid().toString()
    );

    String jsonParameter = new ObjectMapper().writeValueAsString(parameters);
    String baseParameter = Base64.getEncoder().encodeToString(jsonParameter.getBytes());
    String queryParameter = "?parameter=" + baseParameter;
    String redirectUri = this.redirectUri + queryParameter;

    HttpHeaders headers = new HttpHeaders();
    headers.setLocation(URI.create(redirectUri));

    return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
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

  @ResponseBody
  @GetMapping("/{uuid}/exists")
  public ResponseCustom<Boolean> getUserIsExists(@PathVariable("uuid") String uuid){
    boolean exists = userService.checkUserExists(uuid);

    return ResponseCustom.OK(exists);
  }

  @ResponseBody
  @GetMapping("/{uuid}/name")
  public ResponseCustom<String> getUserName(@PathVariable("uuid") String uuid) {
    String username = userService.getUserName(uuid);

    return ResponseCustom.OK(username);
  }

  @Auth
  @ResponseBody
  @GetMapping("/counts")
  public ResponseCustom<GetCountRes> getCounts(@IsLogin LoginStatus loginStatus){
    long chance = userService.getChances(loginStatus.getUserIdx());
    long received = postService.getPost(loginStatus.getUserIdx()).getPostCount();

    GetCountRes response = GetCountRes.builder().chance(chance).received(received).build();
    return ResponseCustom.OK(response);
  }
}
