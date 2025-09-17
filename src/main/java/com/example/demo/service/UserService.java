package com.example.demo.service;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.SignUpDto;
import com.example.demo.entity.User;

public interface UserService {

    User signup(SignUpDto dto);

    User login(LoginDto dto);

    User getUserByEmail(String email);
}
