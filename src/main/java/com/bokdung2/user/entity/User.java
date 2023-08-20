package com.bokdung2.user.entity;

import com.bokdung2.global.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

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

  @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
  @Type(type = "uuid-char")
  private UUID uuid;

  @Builder
  public User(Provider provider, String username, String email, Long kakaoId, UUID uuid) {
    this.provider = provider;
    this.username = username;
    this.email = email;
    this.kakaoId = kakaoId;
    this.uuid = uuid;
  }

  public static void toUpdateChanceCount(User receiver, User sender) {
    receiver.chance = receiver.chance + 1;
    sender.chance = sender.chance - 1;
  }

  public void login() {
    this.loginStatus = true;
  }

  public void logout() {
    this.loginStatus = false;
  }
}
