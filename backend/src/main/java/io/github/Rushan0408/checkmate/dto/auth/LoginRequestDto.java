package io.github.Rushan0408.checkmate.dto.auth;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
    public String toString() {
        return username + "  " + password;
    }
}
