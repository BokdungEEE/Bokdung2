package com.bokdung2.user.entity;

import com.bokdung2.global.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class User extends BaseEntity {

  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long userIdx;

  @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 10")
  private Integer chance = 10;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Provider provider;

  @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
  private Boolean loginStatus = false;

  private String username;
  private String email;
  private Long kakaoId;

  @Builder
  public User(Provider provider, String username, String email, Long kakaoId) {
    this.provider = provider;
    this.username = username;
    this.email = email;
    this.kakaoId = kakaoId;
  }

  public void login() {
    this.loginStatus = true;
  }

  public void logout() {
    this.loginStatus = false;
  }
}
