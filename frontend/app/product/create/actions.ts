"use server";
import { Product } from "@/lib/types";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";

export async function createProduct(formData: FormData) {
  const c = await cookies()
  const token = c.get('token')?.value
  const product: Product = {
    name: formData.get('name')?.toString() || '',
    price: Number(formData.get('price')),
    expirationDate: formData.get('expirationDate')?.toString() || '',
  }
  const response = await fetch(`http://${process.env.NEXT_PUBLIC_API_URL}/api/v1/products`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    },
    body: JSON.stringify(product)
  })
  if (response.ok) {
    redirect('/product')
  }
}

export async function deleteProduct(formData: FormData) {
  const c = await cookies()
  const token = c.get('token')?.value
  const response = await fetch(`http://${process.env.NEXT_PUBLIC_API_URL}/api/v1/products/${formData.get("id")}`, {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    }
  })
  if (response.ok) {
    redirect('/product')
  }
}

