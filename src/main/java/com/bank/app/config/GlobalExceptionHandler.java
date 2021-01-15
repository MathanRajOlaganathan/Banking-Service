package com.bank.app.config;
import com.bank.app.domain.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 12/01/2021
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiCallError<String>> handleNotFoundException(HttpServletRequest request, NotFoundException ex) {
        log.error("NotFoundException {}\n", request.getRequestURI(), ex.toString());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiCallError<>("Not found exception", List.of(ex.getMessage())));

    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiCallError<String>> handleValidationException(HttpServletRequest request, ValidationException ex) {
        log.error("ValidationException {}\n", request.getRequestURI(), ex.toString());

        return ResponseEntity
                .badRequest()
                .body(new ApiCallError<>("Validation exception", List.of(ex.getMessage())));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiCallError<String>> handleMissingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException ex) {
        log.error("handleMissingServletRequestParameterException {}\n", request.getRequestURI(), ex.toString());

        return ResponseEntity
                .badRequest()
                .body(new ApiCallError<>("Missing request parameter", List.of(ex.getMessage())));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiCallError<Map<String, String>>> handleMethodArgumentTypeMismatchException(HttpServletRequest request, MethodArgumentTypeMismatchException ex) {
        log.error("handleMethodArgumentTypeMismatchException {}\n", request.getRequestURI(), ex.toString());

        Map<String, String> details = new HashMap<>();
        details.put("paramName", ex.getName());
        details.put("paramValue", ofNullable(ex.getValue()).map(Object::toString).orElse(""));
        details.put("errorMessage", ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(new ApiCallError<>("Method argument type mismatch", List.of(details)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiCallError<Map<String, String>>> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        log.error("handleMethodArgumentNotValidException {}\n", request.getRequestURI(), ex.toString());

        List<Map<String, String>> details = new ArrayList<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> {
                    Map<String, String> detail = new HashMap<>();
                    detail.put("objectName", fieldError.getObjectName());
                    detail.put("field", fieldError.getField());
                    detail.put("rejectedValue", "" + fieldError.getRejectedValue());
                    detail.put("errorMessage", fieldError.getDefaultMessage());
                    details.add(detail);
                });

        return ResponseEntity
                .badRequest()
                .body(new ApiCallError<>("Method argument validation failed", details));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiCallError<String>> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
        log.error("handleAccessDeniedException {}\n", request.getRequestURI(), ex.toString());

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ApiCallError<>("Access denied!", List.of(ex.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiCallError<String>> handleInternalServerError(HttpServletRequest request, Exception ex) {
        log.error("handleInternalServerError {}\n", request.getRequestURI(), ex.toString());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiCallError<>("Internal server error", List.of(ex.getMessage())));
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class ApiCallError<T> {

        private String message;
        private List<T> details;

    }
}