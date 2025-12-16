package io.github.Rushan0408.checkmate.dto.http;

public class QueueResponse {

    private String status;
    private String gameRoomId;
    private String color;

    public QueueResponse(String status) {
        this.status = status;
    }

    public QueueResponse(String status, String gameRoomId, String color) {
        this.status = status;
        this.gameRoomId = gameRoomId;
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public String getGameRoomId() {
        return gameRoomId;
    }

    public String getColor() {
        return color;
    }
}

