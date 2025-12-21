package io.github.Rushan0408.checkmate.dto.websocket;

public class MatchFoundMessage {

    private String type = "MATCH_FOUND";
    private String gameRoomId;
    private String whitePlayer;
    private String blackPlayer;

    public MatchFoundMessage(String gameRoomId, String whitePlayer, String blackPlayer) {
        this.gameRoomId = gameRoomId;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
    }

    public String getType() {
        return type;
    }

    public String getGameRoomId() {
        return gameRoomId;
    }

    public String getWhitePlayer() {
        return whitePlayer;
    }

    public String getBlackPlayer() {
        return blackPlayer;
    }
}
