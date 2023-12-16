package com.bokdung2.post.entity;

import com.bokdung2.card.entity.Card;
import com.bokdung2.global.entity.BaseEntity;
import com.bokdung2.post.dto.request.PostRequest;
import com.bokdung2.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Post extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long postIdx;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name="userIdx")
  private User user;

  @OneToOne
  private Card card;

  private String message;

  private String senderNickname;

  @Builder
  public Post(User user, Card card, String message, String senderNickname) {
    this.user = user;
    this.card = card;
    this.message = message;
    this.senderNickname = senderNickname;
  }

  public static Post toEntity(User user, Card card, PostRequest postRequest) {
    return Post.builder()
            .user(user)
            .card(card)
            .message(postRequest.getMessage())
            .senderNickname(postRequest.getNickname())
            .build();
  }
}
