package com.pattern.springlab.domain.user.service;

import com.pattern.springlab.domain.user.dto.LoginRequest;
import com.pattern.springlab.domain.user.dto.LoginResponse;
import com.pattern.springlab.domain.user.dto.SignUpRequest;
import com.pattern.springlab.domain.user.entity.User;
import com.pattern.springlab.domain.user.repository.UserRepository;
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
            throw new RuntimeException("이미 사용중인 이메일입니다.");
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
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtProvider.createAccessToken(user.getEmail());
        return new LoginResponse(token);
    }
}