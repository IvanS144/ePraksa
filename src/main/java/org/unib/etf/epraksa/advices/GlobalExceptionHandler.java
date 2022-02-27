package org.unib.etf.epraksa.advices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.unib.etf.epraksa.exceptions.HttpException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpException.class)
    public final ResponseEntity<Object> handleHttpException(HttpException e) {
        if (e.getStatus() == null)
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(e.getData(), e.getStatus());
    }
    @ExceptionHandler(java.sql.SQLIntegrityConstraintViolationException.class)
    public final ResponseEntity<Object> handleSQLIntegrityException()
    {
        return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HandlerMethod handlerMethod) {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(org.hibernate.StaleObjectStateException.class)
    public final ResponseEntity<Object> handleOptimisticLockingException()
    {
        return new ResponseEntity<>("U međuvremenu je došlo do izmjene, provjerite podatke pa pokušajte ponovo", HttpStatus.CONFLICT);
    }
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleException(Exception e){
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
