// com.example.smartbasket_backend.dto.AuthResponse.java
package com.example.smartbasket_backend.dto;

public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
