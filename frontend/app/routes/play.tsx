import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import Board from "~/components/Board";
import GameDetails from "~/components/GameDetails";
import { useAuthStore } from "~/store/auth-store";
import { useRef } from "react";

const Play: React.FC = () => {

  const clientRef = useRef<any>(null);
  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(true);
  const {checkJwt, fetchJwt } = useAuthStore();
  const [game, setGame] = useState("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
  const [boardPieces, setBoardPieces] = useState<string[]>([]);
  const [color, setColor] = useState<"white" | "black">("white");
  const [turn,setTurn] = useState<"white" | "black">("white");
  const [move, setMove] = useState<{ from: string; to: string }>({ from: "", to: "", });


  // on page refresh, makes a websocket connection with server then subscribes to "/user/queue/matchmaking" 
  useEffect(() => {
    let client: any;
    (async () => {
      const { Client } = await import("@stomp/stompjs");
      client = new Client({
        brokerURL: "ws://localhost:8080/ws",
        connectHeaders: {
          Authorization: `Bearer ${fetchJwt()}`
        },
        reconnectDelay: 5000,
        onConnect: () => {
          console.log("✅ Connected");
          client.subscribe("/user/queue/matchmaking", (frame : any) => {
            const payload = JSON.parse(frame.body);
            console.log("🎯 Match found:", payload);
            if ( payload.message == "Match Found" ) {
              setColor(payload.startingColor);
              subscribeToGame();
            } 
          });
        },
        onWebSocketError: e => console.error("WS error", e),
        onStompError: f => console.error("STOMP error", f),
      });
      client.activate();
      clientRef.current = client;
    })();
    return () => client?.deactivate();
  }, []);


  // converts fen string to Board whenever Game(fen string) updates
  useEffect(() => {
    const temp = game.split(" ")[1] === "w" ? "white" : "black";
    setTurn(temp);
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
    console.log(boardString.split(""));
    setBoardPieces(boardString.split(""));
    const t = game.split(" ")[1] === "w" ? "white" : "black";
    setTurn(t);
  }, [game]);


  // subcribes to game enpoint when matchmaking found 
  // set game from the server message
  function subscribeToGame() {
    clientRef.current.subscribe("/user/queue/game", (frame : any) => {
      const payload = JSON.parse(frame.body);
      if ( payload.newMove == "true" ) {
        setGame(payload.fen);
      }
    })
  }


  // handles the Start Matchmaking button
  // when button is clicked an empty body is sent to "/app/matchmaking"
  function handleStartClick() {
    if (!checkJwt()) {
      setIsLoggedIn(false);
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
    console.log("📤 Sent matchmaking request");
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
      />
      <div className=" flex flex-col items-center">
        <GameDetails
          color={color}
          turn={turn}
          move={move}
          game={game} />

        <button className="text-2xl cursor-pointer bg-green-600 px-9 py-1 rounded hover:bg-green-700 mb-1"
          onClick={handleStartClick}
        >Start</button>

        {!isLoggedIn &&
          <p className="text-xs text-red-400 text-center">
            You must login to play
          </p>}
      </div>
    </div>
  );
};

export default Play;
