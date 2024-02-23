package dev.beomseok.boardserver.aop;

import dev.beomseok.boardserver.dto.response.ResponseBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class ControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseBody> RuntimeExceptionHandler(RuntimeException ex) {
        log.error(ex.getMessage());
        return ResponseBody.createFailResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ResponseBody> IllegalStateExceptionHandler(RuntimeException ex) {
        log.error(ex.getMessage());
        return ResponseBody.createFailResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseBody> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        return ResponseBody.createFailResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
