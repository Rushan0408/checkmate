import { create } from "zustand";

type AuthState = {
  checkJwt: () => Promise<boolean>;
  removeJwt: () => Promise<void>;
};

export const useAuthStore = create<AuthState>(() => ({
  checkJwt: async () => {
    try {
      const res = await fetch("/api/auth/validate", {
        method: "GET",
        credentials: "include", 
      });
      return res.ok;
    } catch {
      return false;
    }
  },
  removeJwt: async () => {
    await fetch("/api/auth/logout", {
      method: "POST",
      credentials: "include",
    });
  },
}));