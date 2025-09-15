package com.example.demo.controller;

import com.example.demo.dto.SignUpDto;
import com.example.demo.dto.LoginDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.config.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;  // final, çünkü constructor ile atanacak
    private final JwtUtil jwtUtil;          // final, çünkü constructor ile atanacak

    // Manuel constructor - Spring bu constructor'ı kullanarak injection yapar
    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // Signup
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpDto dto) {
        User savedUser = userService.signup(dto);
        return ResponseEntity.ok("User registered successfully with role: " + savedUser.getRole());
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto dto) {
        User user = userService.login(dto);
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());
        return ResponseEntity.ok(token);
    }
}