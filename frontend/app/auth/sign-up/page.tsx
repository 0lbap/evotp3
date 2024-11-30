import { RegisterForm } from "@/components/register-form";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "Inscription"
}

export default function SignIn() {
  return <div className="flex w-screen h-screen justify-center items-center align-middle">
    <RegisterForm />
  </div>
}
