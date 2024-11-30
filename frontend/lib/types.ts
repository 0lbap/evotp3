export type Product = {
  id?: number
  name: string
  price: number
  expirationDate?: string
}

export type Credentials = {
  email: string
  password: string
}

export type User = Credentials & {
  firstName: string
  lastName: string
  birthDate?: Date
}
