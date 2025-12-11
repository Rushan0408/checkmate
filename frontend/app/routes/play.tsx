import React, { useEffect, useState } from "react";
import Board from "~/components/Board";
import GameDetails from "~/components/GameDetails";

const Play: React.FC = () => {
  const [game, setGame] = useState(
    "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
  );
  const [boardPieces, setBoardPieces] = useState<string[]>([]);
  const [turn, setTurn] = useState<"w" | "b">("w");
  const [move, setMove] = useState<{ from: string; to: string }>({
    from: "",
    to: "",
  });

  useEffect(() => {
    function toDisplay(gameFen: string): string {
      const raw = gameFen.split(" ")[0].split("/").join("");
      let boardRes = "";

      for (let i = 0; i < raw.length; i++) {
        const ch = raw[i];
        if (!isNaN(Number(ch))) {
          boardRes += ".".repeat(Number(ch));
        } else {
          boardRes += ch;
        }
      }
      return boardRes;
    }

    const boardString = toDisplay(game);
    setBoardPieces(boardString.split(""));
    const t = game.split(" ")[1] as "w" | "b";
    setTurn(t);
  }, [game]);

  return (
    <div className="flex flex-row items-center m-10 gap-20 justify-center">
      <Board
        game={game}
        color="w"
        setGame={setGame}
        boardPieces={boardPieces}
        setBoardPieces={setBoardPieces}
        turn={turn}
        setTurn={setTurn}
        move={move}
        setMove={setMove}
      />
      <GameDetails
       turn={turn} 
       move={move}
       game={game} />
    </div>
  );
};

export default Play;
