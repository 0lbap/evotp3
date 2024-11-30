import Link from "next/link"
import Form from "next/form"

import { Button } from "@/components/ui/button"
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Credentials } from "@/lib/types"
import { redirect } from "next/navigation"
import { cookies } from "next/headers"

async function login(formData: FormData) {
  "use server";
  const credentials: Credentials = {
    email: formData.get('email')?.toString() || '',
    password: formData.get('password')?.toString() || ''
  }
  const response = await fetch(`http://${process.env.NEXT_PUBLIC_API_URL}/api/v1/auth/sign-in`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(credentials)
  })
  if (response.ok) {
    const c = await cookies()
    const token = await response.text()
    console.log(token)
    c.set('token', token, { secure: true })
    redirect('/')
  }
}

export function LoginForm() {
  return (
    <Form action={login}>
      <Card className="mx-auto max-w-sm">
        <CardHeader>
          <CardTitle className="text-2xl">Connexion</CardTitle>
          <CardDescription>
            Connectez-vous à votre compte en quelques cliques
          </CardDescription>
        </CardHeader>
        <CardContent>
          <div className="grid gap-4">
            <div className="grid gap-2">
              <Label htmlFor="email">Email</Label>
              <Input
                id="email"
                name="email"
                type="email"
                placeholder="m@example.com"
                required
              />
            </div>
            <div className="grid gap-2">
              <Label htmlFor="password">Password</Label>
              <Input id="password" name="password" type="password" required />
            </div>
            <Button type="submit" className="w-full">
              Connexion
            </Button>
          </div>
          <div className="mt-4 text-center text-sm gap">
            Besoin d&apos;un compte ?
            <Link href="#" className="underline pl-1">
              Inscrivez-vous
            </Link>
          </div>
        </CardContent>
      </Card>
    </Form>
  )
}
