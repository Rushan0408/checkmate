import React from "react";
import { getPieceFile } from "~/utils/getPieceFile";
import { getSquare } from "~/utils/getSquare";

type Move = { from: string; to: string };

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
}) => {


  // handles the player's mouse moves
  async function handleClick(index: number) {
    if (turn !== color) return;
    const square = getSquare(index, color);
    if (move.from === "") {
      if (boardPieces[index] === ".") return;
      setMove({ from: square, to: "" });
      return;
    }
    if (move.from === square) {
      setMove({ from: "", to: "" });
      return;
    }
    const newMove = { from: move.from, to: square };
    setMove(newMove);
    try {
      const res = await apiCall(newMove);
    } catch (err) {
      console.error("move API failed", err);
    } finally {
      setMove({ from: "", to: "" });
    }
  }


  // publishes the new move from client to server on "/app/game"
  async function apiCall(move: Move) {
    console.log("called", move);
    clientRef.current.publish({
      destination: "/app/game",
      body: JSON.stringify(move)
    });
  }

  function isSelected(i: number) {
    const sq = getSquare(i, color);
    return move.from === sq;
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
            border: isSelected(i) ? "1px solid #00FF00" : undefined,
          }}
        >
          {item !== "." && (
            <img
              src={`/${getPieceFile(item)}`}
              alt={item}
              style={{ cursor: "pointer", width: "80%", height: "80%" }}
            />
          )}
        </div>
      ))}
    </div>
  );
};

export default Board;
