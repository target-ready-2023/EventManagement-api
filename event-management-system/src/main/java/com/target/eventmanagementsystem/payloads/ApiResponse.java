package com.target.eventmanagementsystem.payloads;


import lombok.Data;

@Data
public class ApiResponse<T> {
    private T data;
    private String message;
    private boolean success;

    public ApiResponse() {
    }

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    public ApiResponse(boolean success, String message,T data) {
        this.data = data;
        this.message = message;
        this.success = success;
    }
}
