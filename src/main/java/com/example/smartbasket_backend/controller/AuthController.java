// com.example.smartbasket_backend.controller.AuthController.java
package com.example.smartbasket_backend.controller;

import com.example.smartbasket_backend.dto.AuthRequest;
import com.example.smartbasket_backend.dto.AuthResponse;
import com.example.smartbasket_backend.model.User;
import com.example.smartbasket_backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password) {
        return ResponseEntity.ok(authService.register(name, email, password));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
