package kr.co.yourplanet.online.common.exception;

import java.util.HashMap;
import java.util.Map;

import kr.co.yourplanet.online.common.ResponseForm;
import kr.co.yourplanet.core.enums.StatusCode;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String WARN_LOG_TEMPLATE = "[WARN] {}: {}";

    /**
     * 유효하지 않은 입력
     */
    @ExceptionHandler(value = {
            HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class
    })
    protected ResponseEntity<ResponseForm<Void>> handleMissingServletRequestParameterException(Exception e) {
        ResponseForm<Void> exceptionResponse = new ResponseForm<>(StatusCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 제약사항 위반
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseForm<Map>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());});

        ResponseForm<Map> exceptionResponse = new ResponseForm<>(StatusCode.BAD_REQUEST, errors);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ResponseForm<Void>> handleBindException(BindException e) {
        ResponseForm<Void> exceptionResponse = new ResponseForm<>(StatusCode.BAD_REQUEST, e.getAllErrors().get(0).getDefaultMessage(), false);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * 접근 제한
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseForm<Void>> handleAccessDeniedException(AccessDeniedException ex) {
        ResponseForm<Void> exceptionResponse = new ResponseForm<>(StatusCode.FORBIDDEN);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    /**
     * 비즈니스 예외 처리
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ResponseForm<Void>> handleBusinessException(BusinessException e) {
        log.warn(WARN_LOG_TEMPLATE, e.getClass().getSimpleName(), e.getMessage(), e);

        StatusCode statusCode = e.getStatusCode();
        ResponseForm<Void> exceptionResponse = new ResponseForm<>(statusCode, e.getMessage(), false);
        HttpStatus headerStatus;

        try {
            headerStatus = HttpStatus.valueOf(statusCode.getStatusCode());
        } catch(IllegalArgumentException iae) {
            headerStatus = HttpStatus.valueOf(500);
        }

        return new ResponseEntity<>(exceptionResponse, headerStatus);
    }

    /**
     * 기본 예외 처리
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseForm<Void>> hanleException(Exception e) {
        log.error("정의되지 않은 예외 발생: {} {}", e.getClass().getSimpleName(), e.getMessage());

        ResponseForm<Void> exceptionResponse = new ResponseForm<>(StatusCode.NOT_IMPLEMENTED, e.getMessage(), true);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_IMPLEMENTED);
    }
}
