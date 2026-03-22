package com.pattern.springlab.domain.user.service;

import com.pattern.springlab.domain.user.dto.LoginRequest;
import com.pattern.springlab.domain.user.dto.LoginResponse;
import com.pattern.springlab.domain.user.dto.SignUpRequest;
import com.pattern.springlab.domain.user.entity.User;
import com.pattern.springlab.domain.user.repository.UserRepository;
import com.pattern.springlab.global.exception.BusinessException;
import com.pattern.springlab.global.exception.ErrorCode;
import com.pattern.springlab.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public void signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
        User user = new User(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.nickname()
        );
        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        String token = jwtProvider.createAccessToken(user.getEmail());
        return new LoginResponse(token);
    }
}