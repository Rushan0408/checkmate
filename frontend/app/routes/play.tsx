import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import Board from "~/components/Board";
import GameDetails from "~/components/GameDetails";
import { useAuthStore } from "~/store/auth-store";
import { useRef } from "react";

type PossibleMove = { to: string };

const Play: React.FC = () => {

  const clientRef = useRef<any>(null);
  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(true);
  const {checkJwt } = useAuthStore();
  const [game, setGame] = useState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
  const [boardPieces, setBoardPieces] = useState<string[]>([]);
  const [color, setColor] = useState<"white" | "black">("white");
  const [turn,setTurn] = useState<"white" | "black">("white");
  const [move, setMove] = useState<{ from: string; to: string }>({ from: "", to: "", });
  const [possibleMoves, setPossibleMoves] = useState<PossibleMove[]>([]);


  // on page refresh, makes a websocket connection with server then subscribes to "/user/queue/matchmaking"
  useEffect(() => {
    (async () => {
      const { Client } = await import("@stomp/stompjs");
      const client = new Client({
        brokerURL: "ws://localhost:5173/ws",
        connectHeaders: {
          jwt: document.cookie
            .split('; ')
            .find(r => r.startsWith('jwt='))
            ?.split('=')[1] ?? ''
        },
        reconnectDelay: 5000,
        onConnect: () => {
          console.log("✅ Connected");
          client.subscribe("/user/queue/matchmaking", (frame) => {
            const payload = JSON.parse(frame.body);
            console.log("Match Found , Starting color : " , payload.startingColor);
            if (payload.message === "Match Found" || payload.message === "Match Rejoin") {
              setColor(payload.startingColor);
            }
          });
          client.subscribe("/user/queue/game/move", (frame) => {
            const payload = JSON.parse(frame.body);
            if (payload.newMove === true) {
              setGame(payload.fen);
              setPossibleMoves([]);
            }
          });
          client.subscribe("/user/queue/game/possibleMoves", (frame) => {
            const payload = JSON.parse(frame.body);
            if (Array.isArray(payload)) {
              setPossibleMoves(payload);
            } else if (payload?.moves) {
              setPossibleMoves(payload.moves);
            }
          });
          reconnect();
        },
        onWebSocketError: e => console.error("WS error", e),
        onStompError: f => console.error("STOMP error", f),
      });
      clientRef.current = client;
      client.activate();
    })();
    return () => clientRef.current?.deactivate();
  }, []);

  //check if player already in a game
  async function reconnect() {
    if (!checkJwt()) {
      setIsLoggedIn(false);
      navigate('/auth?loginPage=true');
      return;
    }
    await fetch("/api/reconnect", {
      method: 'GET',
      credentials : "include",
    });
  }

  // converts fen string to Board whenever Game(fen string) updates
  useEffect(() => {
    const t = game.split(" ")[1] === "w" ? "white" : "black";
    setTurn(t);

    function toDisplay(gameFen: string): string {
      let placement = gameFen.split(" ")[0];

      if (color === "black") {
        placement = placement.split("").reverse().join("");
      }

      let boardRes = "";
      for (let i = 0; i < placement.length; i++) {
        const ch = placement[i];
        if (!isNaN(Number(ch))) boardRes += ".".repeat(Number(ch));
        else if (ch !== "/") boardRes += ch;
      }

      return boardRes;
    }

    setBoardPieces(toDisplay(game).split(""));
  }, [game, color]);

  // handles the Start Matchmaking button
  // when button is clicked an empty body is sent to "/app/matchmaking"
  async function handleStartClick() {
    const isValid = await checkJwt();
    if (!isValid) {
      setIsLoggedIn(false);
      navigate('/auth?loginPage=true');
      return;
    }
    if (!clientRef.current?.connected) {
      console.warn("WS not connected yet");
      return;
    }
    clientRef.current.publish({
      destination: "/app/matchmaking",
      body: ""
    });
  }

  return (
    <div className="flex flex-row items-center m-10 gap-20 justify-center">
      <Board
        clientRef={clientRef}
        game={game}
        color={color}
        setGame={setGame}
        boardPieces={boardPieces}
        setBoardPieces={setBoardPieces}
        turn={turn}
        setTurn={setTurn}
        move={move}
        setMove={setMove}
        possibleMoves={possibleMoves}
        setPossibleMoves={setPossibleMoves}
      />

      <div className=" flex flex-col items-center">
      <GameDetails
          color={color}
          turn={turn}
          move={move}
          game={game} 
      />        

      <button className="text-2xl cursor-pointer bg-green-600 px-9 py-1 rounded hover:bg-green-700 mb-1"
        onClick={handleStartClick}
      >Start</button>

      {!isLoggedIn &&
        <p className="text-xs text-red-400 text-center">
          You must login to play
        </p> }
      </div>
    </div>
  );
};

export default Play;