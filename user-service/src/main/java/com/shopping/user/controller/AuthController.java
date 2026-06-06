package com.shopping.user.controller;

import com.shopping.user.dto.AuthResponse;
import com.shopping.user.dto.LoginRequest;
import com.shopping.user.dto.RegisterRequest;
import com.shopping.user.dto.UserProfileResponse;
import com.shopping.user.service.AuthService;
import com.shopping.user.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

   
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

   
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMe(
            @AuthenticationPrincipal UserDetails userDetails) {
        UserProfileResponse profile = authService.getProfile(userDetails.getUsername());
        return ResponseEntity.ok(profile);
    }

    
    @GetMapping("/validate")
    public ResponseEntity<Map<String, String>> validateToken(@RequestParam String token) {
        if (jwtService.isValid(token)) {
            String username = jwtService.extractUsername(token);
            String role = jwtService.extractRole(token);
            return ResponseEntity.ok(Map.of(
                    "valid", "true",
                    "username", username,
                    "role", role
            ));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of(
                        "valid", "false",
                        "message", "Token is invalid or expired"
                ));
    }
}
