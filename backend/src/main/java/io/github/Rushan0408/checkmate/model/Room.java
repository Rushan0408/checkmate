package io.github.Rushan0408.checkmate.model;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.Move;

public class Room {

    private final String id;
    private final String whitePlayerId;
    private final String blackPlayerId;

    private final Board board;
    private final GameState gameState;

    public Room(String id, String whitePlayerId, String blackPlayerId) {
        this.id = id;
        this.whitePlayerId = whitePlayerId;
        this.blackPlayerId = blackPlayerId;

        this.board = new Board();
        this.gameState = new GameState(board.getFen(), whitePlayerId);
    }

    public synchronized void makeMove(String playerId, Move move) {
        if (!playerId.equals(gameState.getTurnPlayerId()))
            throw new IllegalStateException("Not your turn");

        if (!board.legalMoves().contains(move))
            throw new IllegalArgumentException("Illegal move");

        board.doMove(move);

        gameState.setFen(board.getFen());
        gameState.setTurnPlayerId(
                board.getSideToMove().isWhite() ? whitePlayerId : blackPlayerId
        );

        if (board.isMated()) gameState.setStatus(GameStatus.CHECKMATE);
        else if (board.isStaleMate()) gameState.setStatus(GameStatus.STALEMATE);
        else if (board.isDraw()) gameState.setStatus(GameStatus.DRAW);
    }

    public String getId() { return id; }
    public GameState getGameState() { return gameState; }
}


