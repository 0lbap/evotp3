import Link from "next/link"

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
import { redirect } from "next/navigation"
import { User } from "@/lib/types"
import Form from "next/form"
import { cookies } from "next/headers"

async function signup(formData: FormData) {
  "use server";
  const credentials: User = {
    firstName: formData.get('firstName')?.toString() || '',
    lastName: formData.get('lastName')?.toString() || '',
    email: formData.get('email')?.toString() || '',
    password: formData.get('password')?.toString() || ''
  }
  const response = await fetch(`http://${process.env.NEXT_PUBLIC_API_URL}/api/v1/auth/sign-up`, {
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

export function RegisterForm() {
  return (
    <Form action={signup}>
      <Card className="mx-auto max-w-sm">
        <CardHeader>
          <CardTitle className="text-2xl">Inscription</CardTitle>
          <CardDescription>
            Connectez-vous à votre compte en quelques cliques
          </CardDescription>
        </CardHeader>
        <CardContent>
          <div className="grid gap-4">
            <div className="grid grid-cols-2 gap-2">
              <div className="grid gap-2">
                <Label htmlFor="firstName">Prénom</Label>
                <Input
                  id="firstName"
                  name="firstName"
                  type="first_name"
                  placeholder="John"
                  required
                />
              </div>
              <div className="grid gap-2">
                <Label htmlFor="lastName">Nom</Label>
                <Input
                  id="lastName"
                  name="lastName"
                  type="last_name"
                  placeholder="DOE"
                  required
                />
              </div>
            </div>
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
              S&apos;inscrire
            </Button>
          </div>
          <div className="mt-4 text-center text-sm gap">
            Besoin d&apos;un compte ?
            <Link href="#" className="underline pl-1">
              Connectez-vous
            </Link>
          </div>
        </CardContent>
      </Card>
    </Form>
  )
}
