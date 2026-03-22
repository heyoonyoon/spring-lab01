package com.pattern.springlab.domain.user.controller;

import com.pattern.springlab.domain.user.dto.LoginRequest;
import com.pattern.springlab.domain.user.dto.LoginResponse;
import com.pattern.springlab.domain.user.dto.SignUpRequest;
import com.pattern.springlab.domain.user.service.UserService;
import com.pattern.springlab.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signUp(@RequestBody SignUpRequest request) {
        userService.signUp(request);
        return ResponseEntity.ok(ApiResponse.ok("회원가입이 완료되었습니다.", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(userService.login(request)));
    }
}