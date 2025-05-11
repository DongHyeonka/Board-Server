package com.fastcampus.board_server.exception;

public class DuplicatedIdException extends RuntimeException {
    public DuplicatedIdException(String message) {
        super(message);
    }
}
