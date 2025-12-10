import { useNavigate } from "react-router";

const home = () => {
  const navigate = useNavigate();
  const handleClick = () => {
    navigate('/play')
  }
  return (
    <div className="flex flex-col justify-center items-center min-h-screen">
      <div className="text-9xl mb-4 ">CheckMate</div>
      <button className="text-3xl cursor-pointer bg-green-400 px-10 py-1 rounded hover:bg-green-500"
        onClick={handleClick}
      >Play</button>
    </div>
  )
}
export default home