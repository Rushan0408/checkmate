import { useEffect, useState } from "react";

function getPieceFile(letter: string) {
  if (letter === ".") return null;

  const map: any = {
    P: "PP.svg",
    N: "NN.svg",
    B: "BB.svg",
    R: "RR.svg",
    Q: "QQ.svg",
    K: "KK.svg",
    p: "p.svg",
    n: "n.svg",
    b: "b.svg",
    r: "r.svg",
    q: "q.svg",
    k: "k.svg"
  };

  return map[letter];
}

const Board = ({ board }: any) => {
  const [boardPieces, setBoardPieces] = useState<string[]>([]);

  useEffect(() => {
    function toDisplay(board: string): string {
      let res = board.split(" ")[0].split("/").join("");
      let boardres = "";

      for (let i = 0; i < res.length; i++) {
        if (!isNaN(Number(res[i]))) {
          boardres += ".".repeat(Number(res[i])); 
        } else {
          boardres += res[i]; 
        }
      }

      return boardres; 
    }

    const boardString = toDisplay(board);
    setBoardPieces(boardString.split("")); 
  }, [board]);

  return (
    <div className="grid grid-cols-8  rounded overflow-hidden">
      {boardPieces.map((item, i) => (
        <div
          key={i}
          className="w-19 h-19"
          style={{
            backgroundColor:
              ((Math.floor(i / 8) + (i % 8)) % 2 === 0 ? "#D2B48C" : "#8B4513"),
          }}
        >
          {item !== "." && (
            <img src={`/${getPieceFile(item)}`} className="" 
              style={{ cursor : "grab" }} />
          )}
        </div>
      ))}
    </div>
  );
};

export default Board;
