package io.github.Rushan0408.checkmate.security;


import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import java.time.Duration;

import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.Rushan0408.checkmate.dto.auth.LoginRequestDto;
import io.github.Rushan0408.checkmate.dto.auth.LoginResponseDto;
import io.github.Rushan0408.checkmate.dto.auth.LogoutResponseDto;
import io.github.Rushan0408.checkmate.dto.auth.SignupResponseDto;
import io.github.Rushan0408.checkmate.model.Player;
import io.github.Rushan0408.checkmate.repository.PlayerRepository;
import jakarta.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(
            LoginRequestDto loginRequestDto,
            HttpServletResponse response
    ) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword()
                )
        );

        Player player = (Player) authentication.getPrincipal();

        String jwt = authUtil.generateAccessToken(player);

        ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
                .httpOnly(false)
                .secure(false)        
                .path("/")
                .maxAge(Duration.ofDays(1))
                .sameSite("Lax")      
                .build();     

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        LoginResponseDto responseDto =
                new LoginResponseDto(player.getId());

        return responseDto;
    }

    public SignupResponseDto signup(LoginRequestDto signupRequestDto) {
        Player player = playerRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);

        if(player != null) throw new IllegalArgumentException("Player already exists");

        player = playerRepository.save(Player.builder()
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .build()
        );

        return new SignupResponseDto(player.getId(), player.getUsername());
    }

    public LogoutResponseDto logout( HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(true)          
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        LogoutResponseDto responseDto = new LogoutResponseDto("Logout Success !");

        return responseDto;
    }
}
