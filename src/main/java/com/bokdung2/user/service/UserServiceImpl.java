package com.bokdung2.user.service;

import com.bokdung2.global.utils.TokenUtils;
import com.bokdung2.global.utils.redis.RedisTemplateService;
import com.bokdung2.user.dto.assembler.UserAssembler;
import com.bokdung2.user.dto.response.LoginTokenRes;
import com.bokdung2.user.entity.Provider;
import com.bokdung2.user.entity.User;
import com.bokdung2.user.exception.UserNotFoundException;
import com.bokdung2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;
  private final KakaoService kakaoService;
  private final TokenUtils tokenUtils;
  private final UserAssembler userAssembler;
  private final RedisTemplateService redisTemplateService;

  @Override
  @Transactional
  public LoginTokenRes kakaoLogin(String authorize_code)
  {
    String kakaoToken = kakaoService.getAccessToken(authorize_code);
    HashMap<String, String> userInfo = kakaoService.getUserInfo(kakaoToken);
    return signIn(userInfo);
  }

  @Transactional
  public LoginTokenRes signIn(HashMap<String, String> userInfo)
  {
    User signInUser = userRepository.findByKakaoIdAndProviderAndIsEnable(Long.valueOf(userInfo.get("id")), Provider.KAKAO, true);

    // 처음 접속 시 회원가입
    if(signInUser==null) {
      signInUser = userAssembler.toEntity(userInfo);
      userRepository.save(signInUser);
    }

    signInUser.login();
    LoginTokenRes loginTokenRes = LoginTokenRes.toDto(tokenUtils.createToken(signInUser));
    redisTemplateService.setUserRefreshToken(String.valueOf(signInUser.getUserIdx()),loginTokenRes.getRefresh_token());
    return loginTokenRes;
  }

  @Override
  @Transactional
  public void logout(Long userIdx) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
    redisTemplateService.deleteUserRefreshToken(String.valueOf(userIdx));
    user.logout();
  }

}