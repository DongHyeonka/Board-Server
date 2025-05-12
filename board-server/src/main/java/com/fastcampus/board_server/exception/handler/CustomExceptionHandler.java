package com.fastcampus.board_server.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fastcampus.board_server.dto.response.CommonResponse;
import com.fastcampus.board_server.exception.BoardServerException;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.http.HttpHeaders;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<Object> handleRuntimeExceptionException(RuntimeException ex) {
        CommonResponse<String> commonResponse = new CommonResponse<>(OK, "RuntimeException", ex.getMessage(),
                ex.getMessage());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(), commonResponse.getStatus());
    }

    @ExceptionHandler({ BoardServerException.class })
    public ResponseEntity<Object> handleBoardServerException(BoardServerException ex) {
        CommonResponse<String> commonResponse = new CommonResponse<>(OK, "BoardServerException", ex.getMessage(),
                ex.getMessage());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(), commonResponse.getStatus());
    }

    @ExceptionHandler({ Exception.class })
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> defaultException(Exception ex) {
        CommonResponse<String> commonResponse = new CommonResponse<>(INTERNAL_SERVER_ERROR, "BoardServerException", ex.getMessage(),
                ex.getMessage());
        return new ResponseEntity<>(commonResponse, new HttpHeaders(), commonResponse.getStatus());
    }
}
