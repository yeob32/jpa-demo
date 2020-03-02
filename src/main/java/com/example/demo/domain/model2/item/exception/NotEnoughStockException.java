package com.example.demo.domain.model2.item.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(String message) {
        super(message);
    }
}
