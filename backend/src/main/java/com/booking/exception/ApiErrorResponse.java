package com.booking.exception;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ApiErrorResponse {
    private int status;
    private String error;
    private Map<String, String> errors; // Chứa { "fieldName": "Thông báo lỗi" }
}