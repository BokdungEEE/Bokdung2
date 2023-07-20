package com.bokdung2.card.entity;

import com.bokdung2.global.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Card extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long cardIdx;

  private String cardName;
  private String cardImgKey_f;
  private String cardImgKey_b;
}
