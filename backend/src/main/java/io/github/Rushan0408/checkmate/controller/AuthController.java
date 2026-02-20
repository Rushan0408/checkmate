package io.github.Rushan0408.checkmate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.Rushan0408.checkmate.dto.auth.LoginRequestDto;
import io.github.Rushan0408.checkmate.dto.auth.LoginResponseDto;
import io.github.Rushan0408.checkmate.dto.auth.LogoutResponseDto;
import io.github.Rushan0408.checkmate.dto.auth.SignupResponseDto;
import io.github.Rushan0408.checkmate.security.AuthService;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        // System.out.println("called \n");
        return ResponseEntity.ok(authService.login(loginRequestDto, response));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody LoginRequestDto signupRequestDto) {
        // System.out.println(" signup controller \n");
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDto> logout( HttpServletResponse response) {
        // System.out.println(" signup controller \n");
        return ResponseEntity.ok(authService.logout(response));
    }

    @GetMapping("/validate")
    public ResponseEntity<Void> validate() {
        return ResponseEntity.ok().build();
    }
}
