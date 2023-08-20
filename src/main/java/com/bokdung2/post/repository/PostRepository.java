package com.bokdung2.post.repository;

import com.bokdung2.post.entity.Post;
import com.bokdung2.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  Integer countByUserAndIsEnable(User user, Boolean isEnable);
}
