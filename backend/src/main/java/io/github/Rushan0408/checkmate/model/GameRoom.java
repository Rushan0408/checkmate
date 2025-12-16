package io.github.Rushan0408.checkmate.model;

import java.util.UUID;

public class GameRoom {

    private String gameRoomId;
    private Player whitePlayer;
    private Player blackPlayer;

    public GameRoom(Player p1, Player p2) {
        this.gameRoomId = UUID.randomUUID().toString();
        this.whitePlayer = p1;
        this.blackPlayer = p2;
    }

    public String getGameRoomId() {
        return gameRoomId;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }
}
