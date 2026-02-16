package io.github.Rushan0408.checkmate.dto.websocket;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PossibleMoveDto {
    private final String to;
}