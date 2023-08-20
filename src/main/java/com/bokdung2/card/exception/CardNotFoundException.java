package com.bokdung2.card.exception;

public class CardNotFoundException extends RuntimeException{
    public CardNotFoundException() {
        super("idx에 일치하는 카드가 존재하지 않습니다.");
    }
}
