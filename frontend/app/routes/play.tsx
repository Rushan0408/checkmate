import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import Board from "~/components/Board";
import GameDetails from "~/components/GameDetails";
import { useAuthStore } from "~/store/auth-store";

const Play: React.FC = () => {
  const navigate = useNavigate();
  const [isLoggedIn , setIsLoggedIn] = useState(true);
  const { checkJwt } = useAuthStore();
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

  function handleStartClick() {
    if ( !checkJwt() ) {
      setIsLoggedIn(false);
      return;
    }  
    
  }

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
      <div className=" flex flex-col items-center">
        <GameDetails
          turn={turn}
          move={move}
          game={game} />

        <button className="text-2xl cursor-pointer bg-green-600 px-9 py-1 rounded hover:bg-green-700 mb-1"
          onClick={handleStartClick}
        >Start</button>

        { !isLoggedIn &&
          <p className="text-xs text-red-400 text-center">
            You must login to play
          </p>}
      </div>

    </div>
  );
};

export default Play;
