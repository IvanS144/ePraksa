package org.unib.etf.epraksa.advices;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.unib.etf.epraksa.exceptions.HttpException;
import org.unib.etf.epraksa.util.LoggingUtil;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpException.class)
    public final ResponseEntity<Object> handleHttpException(HttpException e, HandlerMethod handlerMethod) {
        LoggingUtil.logException(e, getLog(handlerMethod));
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
        LoggingUtil.logException(e, getLog(handlerMethod));
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(org.hibernate.StaleObjectStateException.class)
    public final ResponseEntity<Object> handleOptimisticLockingException()
    {
        return new ResponseEntity<>("U međuvremenu je došlo do izmjene, provjerite podatke pa pokušajte ponovo", HttpStatus.CONFLICT);
    }
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleException(Exception e, HandlerMethod handlerMethod){
        LoggingUtil.logException(e, getLog(handlerMethod));
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Log getLog(HandlerMethod handlerMethod) {
        return LogFactory.getLog(handlerMethod.getMethod().getDeclaringClass());
    }
}
