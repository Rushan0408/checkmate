package io.github.Rushan0408.checkmate.dto.websocket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchFoundMessage {
    private String message ;
    private String startingColor;

    public MatchFoundMessage( String message ,String startingColor ) {
        this.message = message;
        this.startingColor=startingColor;
    }
}
