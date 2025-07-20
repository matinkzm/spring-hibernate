package com.mysoftwareproject.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;
    private final ZonedDateTime zonedDateTime;

    public ApiException(Throwable throwable, HttpStatus httpStatus, ZonedDateTime zonedDateTime, String message) {
        this.throwable = throwable;
        this.httpStatus = httpStatus;
        this.zonedDateTime = zonedDateTime;
        this.message = message;
    }

    public ApiException(HttpStatus httpStatus, ZonedDateTime zonedDateTime, String message) {
        this.throwable=null;
        this.httpStatus = httpStatus;
        this.zonedDateTime = zonedDateTime;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "message='" + message + '\'' +
                ", throwable=" + throwable +
                ", httpStatus=" + httpStatus +
                ", zonedDateTime=" + zonedDateTime +
                '}';
    }
}