package com.bokdung2.user.service;

import com.bokdung2.global.utils.TokenUtils;
import com.bokdung2.user.dto.assembler.UserAssembler;
import com.bokdung2.user.dto.response.LoginTokenRes;
import com.bokdung2.user.entity.Provider;
import com.bokdung2.user.entity.User;
import com.bokdung2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;
  private final KakaoService kakaoService;
  private final TokenUtils tokenUtils;
  private final UserAssembler userAssembler;

  @Override
  public LoginTokenRes kakaoLogin(String authorize_code)
  {
    String kakaoToken = kakaoService.getAccessToken(authorize_code);
    HashMap<String, String> userInfo = kakaoService.getUserInfo(kakaoToken);
    return signIn(userInfo);
  }

  public LoginTokenRes signIn(HashMap<String, String> userInfo)
  {
    User signInUser = userRepository.findByKakaoIdAndProviderAndIsEnable(Long.valueOf(userInfo.get("id")), Provider.KAKAO, true);
    if(signInUser==null) signInUser = userAssembler.toEntity(userInfo);
    signInUser.login();
    userRepository.save(signInUser);
    return LoginTokenRes.toDto(tokenUtils.createToken(signInUser));
  }

}
