import Board from "~/components/Board"
import GameDetails from "~/components/GameDetails"

const play = () => {
  return (
    <div className="flex flex-row items-center m-10 gap-20 justify-center">
        <Board board = {'rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1'}/>
        <GameDetails/>
    </div>
  )
}
export default play