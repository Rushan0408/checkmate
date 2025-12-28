package io.github.Rushan0408.checkmate.model;

public class GameState {

    private String fen;
    private GameStatus status;
    private String turnPlayerId;

    public GameState(String fen, String turnPlayerId) {
        this.fen = fen;
        this.turnPlayerId = turnPlayerId;
        this.status = GameStatus.ONGOING;
    }

    public String getFen() { return fen; }
    public void setFen(String fen) { this.fen = fen; }

    public String getTurnPlayerId() { return turnPlayerId; }
    public void setTurnPlayerId(String turnPlayerId) { this.turnPlayerId = turnPlayerId; }

    public GameStatus getStatus() { return status; }
    public void setStatus(GameStatus status) { this.status = status; }
}

