package pl.easyprogramming.bank.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalStateException.class})
    protected ResponseEntity<Object> handleIllegalStateErros(RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {AuthorizationServiceException.class})
    protected ResponseEntity<Object> handleVAuthorizationErros(RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }


    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<Object> handleVEntityNOtFoundErros(RuntimeException ex, WebRequest request) {

        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (ex.getCause().getCause() instanceof ValidationException) {

            return handleExceptionInternal(ex, ex.getCause().getCause().getMessage(),
                    new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
        }

        return new ResponseEntity(ex, headers, status);
    }
}