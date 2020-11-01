package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.exception.IdNotExistException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
@Component
@AllArgsConstructor
public class GlobalExceptionHandler {
    /**
     * Handle Employee Id not found exception
     */
    @ExceptionHandler()
    public ResponseEntity<String> handleIdNotExistException(IdNotExistException e) {
        String errorMsg = "Employee ID not exists";
        log.info(errorMsg);
        return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
    }

    /**
     * Unknown error handler
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handle(Throwable e) {

        log.error("Unknown error", e);

        return new ResponseEntity<>(
                "Unknown error: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
