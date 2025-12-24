import { useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { useAuthStore } from "~/store/auth-store";

const Header = () => {
  useEffect(() => {
    if (checkJwt()) setLoggedIn(true);
    else setLoggedIn(false);
  }, [])
  const [loggedIn, setLoggedIn] = useState(false);
  const { removeJwt, checkJwt } = useAuthStore();
  const navigate = useNavigate();
  const handleSignUpClick = () => {
    navigate('/auth?loginPage=false')
  }
  const handleLoginClick = () => {
    navigate('/auth?loginPage=true')
  }
  const handleLogOut = () => {
    removeJwt();
    setLoggedIn(false);
  }

  return (
    <div className="flex flex-row-reverse p-4 gap-4 text-white ">
      {loggedIn &&
        <button className="text-2xl cursor-pointer bg-green-600  rounded hover:bg-green-700 w-25"
          onClick={handleLogOut}
        >logout</button>}
      {!loggedIn && <>
        <button className="text-2xl cursor-pointer bg-green-600  rounded hover:bg-green-700 w-25"
          onClick={handleSignUpClick}
        >signup</button>
        <button className="text-2xl cursor-pointer bg-green-600  rounded hover:bg-green-700 w-25 p-1"
          onClick={handleLoginClick}
        >login</button>
      </>
      }

    </div>
  )
}
export default Header

