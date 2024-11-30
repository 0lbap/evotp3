import { LoginForm } from "@/components/login-form";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "Connexion"
}

export default function SignIn() {
  return <div className="flex w-screen h-screen justify-center items-center align-middle">
    <LoginForm />
  </div>
}
