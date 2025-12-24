import { create } from "zustand";

type AuthState = {
  checkJwt: () => boolean;
  setJwt: (value: string) => void; 
  fetchJwt : () => string | null;
  removeJwt : () => void;
};

export const useAuthStore = create<AuthState>((set) => ({

  checkJwt : () => {
    if ( window.localStorage.getItem("jwt") ) return true;
    else return false;
  },

  setJwt : (value) => {
    window.localStorage.setItem("jwt" , value)
  },

  fetchJwt : () => {
    return window.localStorage.getItem("jwt");
  },
  removeJwt() {
    window.localStorage.removeItem('jwt');
  },
}));
