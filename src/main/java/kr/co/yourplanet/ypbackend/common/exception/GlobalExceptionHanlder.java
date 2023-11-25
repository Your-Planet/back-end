package kr.co.yourplanet.ypbackend.common.exception;

import kr.co.yourplanet.ypbackend.common.ResponseForm;
import kr.co.yourplanet.ypbackend.common.enums.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHanlder {

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<ResponseForm> illegalStateException(IllegalStateException e) {
        ResponseForm exceptionResponse = new ResponseForm(StatusCode.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseForm> illegalStateException(Exception e) {
        ResponseForm exceptionResponse = new ResponseForm(StatusCode.BAD_REQUEST, "정의되지 않은 예외 발생 \n" + e.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
