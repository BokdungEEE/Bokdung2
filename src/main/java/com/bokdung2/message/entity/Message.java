package com.bokdung2.message.entity;

import com.bokdung2.global.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Message extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long messageIdx;

  private String contents;
}
