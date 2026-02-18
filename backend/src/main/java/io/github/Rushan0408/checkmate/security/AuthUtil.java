package io.github.Rushan0408.checkmate.security;

import io.github.Rushan0408.checkmate.model.Player;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
// import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class AuthUtil {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Player player) {
        return Jwts.builder()
                .subject(player.getId())   
                .claim("username", player.getUsername())
                .claim("roles",
                   player.getAuthorities().stream()
                         .map(a -> a.getAuthority())
                         .toList())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(getSecretKey())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims =  Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Object rolesObj = claims.get("roles");
        if (rolesObj instanceof List<?> rolesList) {
            return rolesList.stream()
                    .map(Object::toString)
                    .toList();
        }
        return List.of();
    }

    //unused so far
    // public void addJwtCookie(HttpServletResponse response, String jwt) {
    //     ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
    //             .httpOnly(true)
    //             .secure(false)     // true in production (HTTPS)
    //             .sameSite("Lax")   // Strict breaks login sometimes
    //             .path("/")
    //             .maxAge(60 * 60 * 60)   // 60 hour
    //             .build();
    //     response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    // }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public String getUsernameClaim(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("username", String.class);
    }

}
