package com.bokdung2.post.repository;

import com.bokdung2.post.entity.Post;
import com.bokdung2.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  Integer countByUserAndIsEnable(User user, Boolean isEnable);

  List<Post> findByUserAndIsEnable(User user, Boolean isEnable);

  Optional<Post> findByPostIdxAndIsEnable(Long postIdx, Boolean isEnable);
}
