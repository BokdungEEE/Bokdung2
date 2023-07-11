package com.bokdung2.user.entity;

import com.bokdung2.global.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class User extends BaseEntity {

  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long userIdx;

  private String username;
  private String email;
  private Integer chance;
}
