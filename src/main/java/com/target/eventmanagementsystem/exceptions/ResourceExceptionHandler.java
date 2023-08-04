package com.target.eventmanagementsystem.exceptions;

import com.target.eventmanagementsystem.payloads.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleApiException(ApiException ex) {
        ApiResponse<Void> response = new ApiResponse<>(ex.getMessage() );
        return ResponseEntity.status(ex.getStatus()).body(response);
    }
}
