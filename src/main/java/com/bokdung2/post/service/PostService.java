package com.bokdung2.post.service;

import com.bokdung2.card.entity.Card;
import com.bokdung2.card.exception.CardNotFoundException;
import com.bokdung2.card.repository.CardRepository;
import com.bokdung2.post.dto.request.PostRequest;
import com.bokdung2.post.dto.response.PostResponse;
import com.bokdung2.post.entity.Post;
import com.bokdung2.post.repository.PostRepository;
import com.bokdung2.user.entity.User;
import com.bokdung2.user.exception.UserNotFoundException;
import com.bokdung2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final CardRepository cardRepository;

  @Transactional
  public void savePost(Long userIdx, UUID uuid, PostRequest postRequest) {
    User sender = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
    User receiver = userRepository.findByUuidAndIsEnable(uuid, true).orElseThrow(UserNotFoundException::new);
    Card card = cardRepository.findByCardIdxAndIsEnable(postRequest.getCardIdx(), true).orElseThrow(CardNotFoundException::new);

    User.toUpdateChanceCount(receiver, sender);

    postRepository.save(Post.toEntity(receiver, card, postRequest));
  }

  public Integer countPost(Long userIdx) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
    return postRepository.countByUserAndIsEnable(user, true);
  }

  public PostResponse getPost(Long userIdx) {
    User user = userRepository.findByUserIdxAndIsEnable(userIdx, true).orElseThrow(UserNotFoundException::new);
    List<Post> posts = postRepository.findByUserAndIsEnable(user, true);
    return PostResponse.toDto(posts);
  }
}
