package com.booking.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiResponse<T> {
    // Getters và Setters (hoặc dùng Lombok @Getter @Setter)
    private boolean success;
    private String message;
    private T data;

    // Constructor ẩn
    private ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Phương thức static tiện ích cho trường hợp lỗi
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null);
    }

}
