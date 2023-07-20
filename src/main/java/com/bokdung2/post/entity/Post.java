package com.bokdung2.post.entity;

import com.bokdung2.card.entity.Card;
import com.bokdung2.global.entity.BaseEntity;
import com.bokdung2.message.entity.Message;
import com.bokdung2.user.entity.User;
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

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "messageIdx")
  private Message message;

  @OneToOne
  private Card card;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PostType postType;
}
