package com.bokdung2.post.controller;

import com.bokdung2.global.dto.ResponseCustom;
import com.bokdung2.global.resolver.Auth;
import com.bokdung2.global.resolver.IsLogin;
import com.bokdung2.global.resolver.LoginStatus;
import com.bokdung2.post.dto.request.PostRequest;
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

  @Auth
  @ResponseBody
  @GetMapping("/count")
  public ResponseCustom<Integer> countPost(
          @IsLogin LoginStatus loginStatus
  )
  {
    return ResponseCustom.OK(postService.countPost(loginStatus.getUserIdx()));
  }
}
