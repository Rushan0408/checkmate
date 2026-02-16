import React from "react";
import { getPieceFile } from "~/utils/getPieceFile";
import { getSquare } from "~/utils/getSquare";

type Move = { from: string; to: string };
type PossibleMove = { to: string };

interface BoardProps {
  clientRef: any;
  game: string;
  color: "white" | "black";
  setGame: (fen: string) => void;
  boardPieces: string[];
  setBoardPieces: (bp: string[]) => void;
  turn: "white" | "black";
  setTurn: (t: "white" | "black") => void;
  move: Move;
  setMove: (m: Move) => void;
  possibleMoves: PossibleMove[];
  setPossibleMoves: (m: PossibleMove[]) => void;
}

const Board: React.FC<BoardProps> = ({
  clientRef,
  game,
  color,
  setGame,
  boardPieces,
  setBoardPieces,
  turn,
  setTurn,
  move,
  setMove,
  possibleMoves,
  setPossibleMoves
}) => {
  // handles the player's clicks on the board 
  async function handleClick(index: number) {
    if (turn !== color) return;
    const square = getSquare(index, color);

    // If no piece is selected, select one
    if (move.from === "") {
      if (boardPieces[index] === ".") return;
      setMove({ from: square, to: "" });
      apiCallPossibleMoves({ from: square, to: "" });
      return;
    }

    // If clicking the same piece, deselect it
    if (move.from === square) {
      setMove({ from: "", to: "" });
      setPossibleMoves([]);
      return;
    }

    // If clicking the another piece of same color (its own piece) 
    // then reassign the 'from' to that new piece and call
    // apiCallMakeMove
    const clickedPiece = boardPieces[index];
    if (isOwnPiece(clickedPiece)) {
      setMove({ from: square, to: "" });
      apiCallPossibleMoves({ from: square, to: "" });
      return;
    }

    // Try to make the move
    const newMove = { from: move.from, to: square };
    setMove(newMove);
    try {
      await apiCallMakeMove(newMove);
    } catch (err) {
      console.error("move API failed", err);
    } finally {
      setMove({ from: "", to: "" });
      setPossibleMoves([]);
    }
  }

  // publishes the new move from client to server on "/app/game"
  async function apiCallMakeMove(move: Move) {
    console.log("called : ", move);
    clientRef.current.publish({
      destination: "/app/game/move",
      body: JSON.stringify(move)
    });
  }

  async function apiCallPossibleMoves(move: Move) {
    console.log("called apiCallPossibleMoves : ", move);
    clientRef.current.publish({
      destination: "/app/game/possibleMoves",
      body: JSON.stringify(move)
    });
  }

  function isSelected(i: number) {
    const sq = getSquare(i, color);
    return move.from === sq;
  }

  function isPossible(i: number) {
    if (!possibleMoves || possibleMoves.length === 0) return false;
    const sq = getSquare(i, color);
    return possibleMoves.some(m => m.to === sq);
  }

  function isOwnPiece(piece: string): boolean {
    if (piece === ".") return false;
    if (color === "white") {
      return piece === piece.toUpperCase();
    } else {
      return piece === piece.toLowerCase();
    }
  }

  return (
    <div className="grid grid-cols-8 rounded overflow-hidden">
      {boardPieces.map((item, i) => (
        <div
          key={i}
          onClick={() => handleClick(i)}
          className="w-19 h-19 relative select-none"
          style={{
            backgroundColor:
              (Math.floor(i / 8) + (i % 8)) % 2 === 0 ? "#D2B48C" : "#8B4513",
            width: 64,
            height: 64,
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            boxSizing: "border-box",
            cursor: "pointer",
          }}
        >
          {/* 🔴 red square overlay for selected piece */}
          {isSelected(i) && (
            <div
              style={{
                position: "absolute",
                inset: 0,
                backgroundColor: "rgba(255, 0, 0, 0.3)",
                pointerEvents: "none",
                zIndex: 0,
              }}
            />
          )}

          {/* 🔴 red outline for possible moves */}
          {isPossible(i) && (
            <div
              style={{
                position: "absolute",
                inset: 0,
                border: "3px solid rgba(255, 0, 0, 0.7)",
                pointerEvents: "none",
                zIndex: 1,
              }}
            />
          )}

          {/* Chess piece */}
          {item !== "." && (
            <img
              src={`/${getPieceFile(item)}`}
              alt={item}
              style={{
                cursor: "pointer",
                width: "80%",
                height: "80%",
                position: "relative",
                zIndex: 2, // piece stays above overlay and dots
              }}
            />
          )}
        </div>
      ))}
    </div>
  );
};

export default Board;