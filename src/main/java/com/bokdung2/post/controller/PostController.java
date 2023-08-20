package com.bokdung2.post.controller;

import com.bokdung2.global.dto.ResponseCustom;
import com.bokdung2.global.resolver.Auth;
import com.bokdung2.global.resolver.IsLogin;
import com.bokdung2.global.resolver.LoginStatus;
import com.bokdung2.post.dto.response.DetailPostResponse;
import com.bokdung2.post.dto.request.PostRequest;
import com.bokdung2.post.dto.response.PostResponse;
import com.bokdung2.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

  private final PostService postService;

  // 포스트 저장
  @Auth
  @ResponseBody
  @PostMapping("/save/{uuid}")
  public ResponseCustom<Void> savePost(
          @IsLogin LoginStatus loginStatus,
          @PathVariable UUID uuid,
          @RequestBody PostRequest postRequest
  )
  {
    postService.savePost(loginStatus.getUserIdx(), uuid, postRequest);
    return ResponseCustom.OK();
  }

  // 받은 포스트 개수 조회
  @Auth
  @ResponseBody
  @GetMapping("/count")
  public ResponseCustom<Integer> countPost(
          @IsLogin LoginStatus loginStatus
  )
  {
    return ResponseCustom.OK(postService.countPost(loginStatus.getUserIdx()));
  }

  // 받은 포스트 전체 조회
  @Auth
  @ResponseBody
  @GetMapping("")
  public ResponseCustom<PostResponse> getPost(
          @IsLogin LoginStatus loginStatus
  )
  {
    return ResponseCustom.OK(postService.getPost(loginStatus.getUserIdx()));
  }

  // 포스트 상세조회
  @Auth
  @ResponseBody
  @GetMapping("/{postIdx}")
  public ResponseCustom<DetailPostResponse> getDetailPost(
          @IsLogin LoginStatus loginStatus,
          @PathVariable Long postIdx
  )
  {
    return ResponseCustom.OK(postService.getDetailPost(loginStatus.getUserIdx(), postIdx));
  }

}
