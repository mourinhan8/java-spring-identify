package com.mourinhan.identify_service.exception;

import com.mourinhan.identify_service.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingException(Exception exception) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode().value());
        apiResponse.
                setMessage(Objects.
                        requireNonNullElse(
                                exception.getMessage(),
                                "Internal server error")
                );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        HttpStatus statusCode = errorCode.getCode();

        return ResponseEntity.status(statusCode).body(
                ApiResponse.builder()
                        .code(statusCode.value())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        HttpStatus statusCode = errorCode.getCode();
        apiResponse.setCode(statusCode.value());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(statusCode).body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e) {

        }
        ApiResponse apiResponse = new ApiResponse();
        HttpStatus statusCode = errorCode.getCode();
        apiResponse.setCode(statusCode.value());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(statusCode).body(apiResponse);
    }
}
