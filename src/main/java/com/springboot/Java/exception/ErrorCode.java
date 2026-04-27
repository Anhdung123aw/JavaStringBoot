package com.springboot.Java.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;



@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001,"User existed",HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(403, "You do not permisson",HttpStatus.FORBIDDEN),
    USER_NOT_EXISTED(1002,"User not existed",HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1003,"Unauthenticated",HttpStatus.UNAUTHORIZED);


    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message,HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

}
