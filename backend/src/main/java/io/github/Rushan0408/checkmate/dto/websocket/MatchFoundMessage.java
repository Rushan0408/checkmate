package io.github.Rushan0408.checkmate.dto.websocket;

public class MatchFoundMessage {

    private String type = "MATCH_FOUND";
    private String gameRoomId;
    private String color;

    public MatchFoundMessage(String gameRoomId, String color) {
        this.gameRoomId = gameRoomId;
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public String getGameRoomId() {
        return gameRoomId;
    }

    public String getColor() {
        return color;
    }
}
