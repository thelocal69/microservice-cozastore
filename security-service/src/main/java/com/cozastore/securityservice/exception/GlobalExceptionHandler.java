package com.cozastore.securityservice.exception;

import com.cozastore.securityservice.payload.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.sql.Timestamp;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handleException(Exception ex, WebRequest request){
        log.info(ex.getMessage());
       return ResponseError
               .builder()
               .timestamp(new Timestamp(System.currentTimeMillis()))
               .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
               .error(HttpStatus.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
               .message(ex.getMessage())
               .path(this.getServletPath(request))
               .build();
    }

    @ExceptionHandler(AuthenticateException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseError handleAuthenticateException(Exception ex, WebRequest request){
        log.info(ex.getMessage());
        return ResponseError
                .builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .error(HttpStatus.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .message(ex.getMessage())
                .path(this.getServletPath(request))
                .build();
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseError handleForbiddenException(Exception ex, WebRequest request){
        log.info(ex.getMessage());
        return ResponseError
                .builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .statusCode(HttpStatus.FORBIDDEN.value())
                .error(HttpStatus.valueOf(HttpStatus.FORBIDDEN.value()))
                .message(ex.getMessage())
                .path(this.getServletPath(request))
                .build();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handleBadRequestException(Exception ex, WebRequest request){
        log.info(ex.getMessage());
        return ResponseError
                .builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message(ex.getMessage())
                .path(this.getServletPath(request))
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handleNotFoundException(Exception ex, WebRequest request){
        log.info(ex.getMessage());
        return ResponseError
                .builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .statusCode(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.valueOf(HttpStatus.NOT_FOUND.value()))
                .message(ex.getMessage())
                .path(this.getServletPath(request))
                .build();
    }

    @ExceptionHandler(AccountException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public ResponseError handleAccountException(Exception ex, WebRequest request){
        log.info(ex.getMessage());
        return ResponseError
                .builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .statusCode(HttpStatus.NOT_IMPLEMENTED.value())
                .error(HttpStatus.valueOf(HttpStatus.NOT_IMPLEMENTED.value()))
                .message(ex.getMessage())
                .path(this.getServletPath(request))
                .build();
    }

    private String getServletPath(WebRequest webRequest) {
        ServletWebRequest servletRequest = (ServletWebRequest) webRequest;
        return servletRequest.getRequest().getServletPath();
    }
}
