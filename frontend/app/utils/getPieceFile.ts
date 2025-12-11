export function getPieceFile(letter: string) {
  if (letter === ".") return null;

  const map : any = {
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