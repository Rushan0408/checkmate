package io.github.Rushan0408.checkmate.dto.websocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchRejoin {
    private String message ;
    private String startingColor;

}
