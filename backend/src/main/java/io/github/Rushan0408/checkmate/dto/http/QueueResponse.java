package io.github.Rushan0408.checkmate.dto.http;

public class QueueResponse {

    private String status;
    private String color;
    private String gameRoomId;

    public QueueResponse(String status) {
        this.status = status;
    }

    public QueueResponse(String status, String color) {
        this.status = status;
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public String getColor() {
        return color;
    }

    public String getGameRoomId() {
        return gameRoomId;
    }

    public void setGameRoomId(String gameRoomId) {
        this.gameRoomId = gameRoomId;
    }
}

