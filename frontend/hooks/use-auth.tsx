"use client";

import { getCookie } from "cookies-next";
import { createContext, PropsWithChildren, useContext, useEffect, useState } from "react";

type AuthProps = {
  token: string | undefined
  isLoggedIn: boolean
}

const Auth = createContext<AuthProps>({
  token: "",
  isLoggedIn: false
})

export function AuthProvider({ children }: PropsWithChildren) {
  const [token, setToken] = useState<string>()
  useEffect(() => {
    const cookieToken = getCookie('token')
    if (typeof cookieToken === "string") setToken(cookieToken)
    else cookieToken?.then(setToken)
  }, [])
  return <Auth.Provider value={{ token: token, isLoggedIn: !!token }}>
    {children}
  </Auth.Provider>
}

export function useAuth() {
  return  useContext(Auth)
}
