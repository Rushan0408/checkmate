const GameDetails = ( { turn , move , game } : any) => {
  return (
    <div className="w-1/4 border ">
      <h1>Game Details </h1>
      <h2>turn : {turn}</h2>
      <h2>number of moves played : {game.split(" ")[5]-1}</h2>
    </div>

  )
}
export default GameDetails