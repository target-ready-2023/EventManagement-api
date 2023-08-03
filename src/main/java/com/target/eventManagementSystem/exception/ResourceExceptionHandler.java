package com.target.eventManagementSystem.exceptions;

import com.target.eventManagementSystem.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(com.target.eventManagementSystem.exceptions.ApiException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundException(com.target.eventManagementSystem.exceptions.ApiException ex) {
        ApiResponse<Void> response = new ApiResponse<>(null,ex.getMessage() );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }


}
