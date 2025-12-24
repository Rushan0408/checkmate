import { useNavigate } from "react-router";
import Header from "~/components/Header";

const home = () => {
  const navigate = useNavigate();
  const handleClick = () => {
    navigate('/play')
  }
  return (
    <div>
      <Header/>
      <div className="flex flex-col justify-center items-center min-h-screen text-white">
        <div className="text-7xl mb-4 ">CheckMate</div>
        <button className="text-2xl cursor-pointer bg-green-600 px-9 py-1 rounded hover:bg-green-700"
          onClick={handleClick}
        >Play</button>
      </div>
    </div>
  )
}
export default home