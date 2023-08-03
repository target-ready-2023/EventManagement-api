package com.target.eventmanagementsystem.exceptions;

import com.target.eventmanagementsystem.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(ApiException ex) {
        ApiResponse<Void> response = new ApiResponse<>(null,ex.getMessage() );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

//    @ExceptionHandler(BadRequestException.class)
//    public ResponseEntity<ApiResponse<Void>> handleBadRequestException(BadRequestException ex) {
//        ApiResponse<Void> response = new ApiResponse<>(null,ex.getMessage() );
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//    }
}
