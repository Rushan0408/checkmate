package io.github.Rushan0408.checkmate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.Rushan0408.checkmate.dto.auth.LoginRequestDto;
import io.github.Rushan0408.checkmate.dto.auth.LoginResponseDto;
import io.github.Rushan0408.checkmate.dto.auth.SignupResponseDto;
import io.github.Rushan0408.checkmate.security.AuthService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        System.out.println("called \n");
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody LoginRequestDto signupRequestDto) {
        System.out.println(" signup controller \n");
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }
    // /me
    // /logout
}
