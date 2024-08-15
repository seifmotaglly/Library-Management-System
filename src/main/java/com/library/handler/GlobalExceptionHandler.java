package com.library.handler;

import java.util.HashSet;
import java.util.Set;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.mail.MessagingException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException exp) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .errorCode(ErrorCodes.ACCOUNT_DISABLED.getCode())
                                .errorDetails(ErrorCodes.ACCOUNT_DISABLED.getDescription())
                                .errorMessage(exp.getMessage())
                                .build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(BadCredentialsException exp) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .errorCode(ErrorCodes.BAD_CREDENTIALS.getCode())
                                .errorDetails(ErrorCodes.BAD_CREDENTIALS.getDescription())
                                .errorMessage(exp.getMessage())
                                .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleException(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleException(DataIntegrityViolationException exp) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                        .errorCode(ErrorCodes.ACCOUNT_USED.getCode())
                        .errorDetails(ErrorCodes.ACCOUNT_USED.getDescription())
                        .errorMessage(exp.getMessage())
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(NotFoundException exp) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ExceptionResponse.builder()
                        .errorCode(ErrorCodes.NO_CODE.getCode())
                        .errorDetails(ErrorCodes.NO_CODE.getDescription())
                        .errorMessage(exp.getMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
        //TOdo: loging method
        exp.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .errorDetails("Internal server error")
                                .errorMessage(exp.getMessage())
                                .build());
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .errorMessage(exp.getMessage())
                                .build());
    }
}
