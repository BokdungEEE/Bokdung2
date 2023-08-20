package com.bokdung2.card.repository;

import com.bokdung2.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
  Optional<Card> findByCardIdxAndIsEnable(Long cardIdx, Boolean isEnable);
}
