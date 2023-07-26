package com.bokdung2.user.repository;

import com.bokdung2.user.entity.Provider;
import com.bokdung2.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  User findByKakaoIdAndProviderAndIsEnable(Long kakaoId, Provider provider, Boolean isEnable);

  Optional<User> findByUserIdxAndIsEnable(Long userIdx, Boolean isEnable);
}
