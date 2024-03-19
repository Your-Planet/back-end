package kr.co.yourplanet.ypbackend.common.exception;

import kr.co.yourplanet.ypbackend.common.ResponseForm;
import kr.co.yourplanet.ypbackend.common.enums.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ResponseForm<Void>> handleBusinessException(BusinessException e) {
        StatusCode statusCode = e.getStatusCode();
        ResponseForm<Void> exceptionResponse = new ResponseForm<>(statusCode);
        HttpStatus headerStatus;
        
        try {
            headerStatus = HttpStatus.valueOf(statusCode.getStatusCode());
        } catch(IllegalArgumentException iae) {
            headerStatus = HttpStatus.valueOf(500);
        }

        return new ResponseEntity<>(exceptionResponse, headerStatus);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<ResponseForm<Void>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        ResponseForm<Void> exceptionResponse = new ResponseForm<>(StatusCode.INVALID_INPUT_VALUE);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ResponseForm<Void>> handleBindException(BindException e) {
        ResponseForm<Void> exceptionResponse = new ResponseForm<>(StatusCode.BAD_REQUEST);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseForm<Void>> hanleException(Exception e) {
        log.error("정의되지 않은 예외 발생 : ", e);
        ResponseForm<Void> exceptionResponse = new ResponseForm<>(StatusCode.NOT_IMPLEMENTED);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_IMPLEMENTED);
    }

}
