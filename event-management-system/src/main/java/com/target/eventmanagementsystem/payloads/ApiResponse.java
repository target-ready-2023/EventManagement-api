package com.target.eventmanagementsystem.payloads;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse {

    public static ResponseEntity<Object> apiResponse(String message, HttpStatus httpStatus, Object responseData)
    {
        Map<String,Object> response = new HashMap<>();
        response.put("message",message);
        response.put("httpStatus",httpStatus);
        response.put("data",responseData);

        return new ResponseEntity<>(response,httpStatus);
    }

}
