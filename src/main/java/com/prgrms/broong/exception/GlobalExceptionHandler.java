package com.prgrms.broong.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException(NotFoundException e) {

        log.error("NotFoundExceptionHandler : {}", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorResponse.of(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> field(MethodArgumentNotValidException e) {
        final BindingResult bindingResult = e.getBindingResult();
        StringBuilder stringBuilder = new StringBuilder();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            stringBuilder.append(fieldError.getField()).append(":");
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append(",");
        }

        ErrorResponse errorResponse = new ErrorResponse(stringBuilder.toString());
        log.error("MethodArgumentNotValidException: {}", stringBuilder.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errorResponse);
//            .body(ErrorResponse.of(stringBuilder.toString()));
    }

}