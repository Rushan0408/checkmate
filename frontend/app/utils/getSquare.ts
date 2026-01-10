export function getSquare(i: number, color: "white" | "black") {
  const file = color === "white" ? i % 8 : 7 - (i % 8);
  const rank = color === "white"
    ? 8 - Math.floor(i / 8) - 1
    : Math.floor(i / 8);

  return String.fromCharCode(97 + file) + (rank + 1);
}
