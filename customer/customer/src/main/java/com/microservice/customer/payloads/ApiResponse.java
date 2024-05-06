package com.microservice.customer.payloads;

import java.time.LocalDateTime;

public class ApiResponse {

    private String message;
    private boolean success;
    private String timestamp;

    public ApiResponse(String message, boolean success, LocalDateTime timestamp) {
        this.message = message;
        this.success = success;
        this.timestamp = timestamp.toString();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
