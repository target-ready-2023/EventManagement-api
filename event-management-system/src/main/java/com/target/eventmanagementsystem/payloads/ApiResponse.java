//package com.target.eventmanagementsystem.payloads;
//
//import lombok.*;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.HashMap;
//import java.util.Map;
////@NoArgsConstructor
////@AllArgsConstructor
////@Getter
////@Setter
//
//@Data
//public class ApiResponse<T> {
//
//    private T data;
//    private String error;
//
//    public ApiResponse(T data, String error)
//    {
//        data = this.data;
//        error = this.error;
//    }
//
//    public ApiResponse(String error)
//    {
//        error = this.error;
//    }
//}

package com.target.eventmanagementsystem.payloads;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ApiResponse<T> {

    private T data;
    private String message;

    public ApiResponse() {
    }

    public ApiResponse(T data) {
        this.data = data;
    }

    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }
}
