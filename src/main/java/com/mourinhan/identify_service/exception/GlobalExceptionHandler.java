package com.mourinhan.identify_service.exception;

import com.mourinhan.identify_service.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String MIN_ATTRIBUTE = "min";
    private static final String MAX_ATTRIBUTE = "max";

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

        Map<String, Object> attributes = new HashMap<>();
        try {
            errorCode = ErrorCode.valueOf(enumKey);
            var constraintViolation = exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();

            log.info(attributes.toString());
        } catch (IllegalArgumentException e) {

        }
        ApiResponse apiResponse = new ApiResponse();
        HttpStatus statusCode = errorCode.getCode();
        apiResponse.setCode(statusCode.value());
        apiResponse.setMessage(Objects.nonNull(attributes) ? mapAttribute(errorCode.getMessage(), attributes) : errorCode.getMessage());

        return ResponseEntity.status(statusCode).body(apiResponse);
    }

    private String mapAttribute(String message, Map<String, Object> attributes ) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));

        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }
}
