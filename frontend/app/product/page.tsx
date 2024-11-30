import ProductTable from "@/components/product/table";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Product } from "@/lib/types";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "Liste des produits"
};

export const dynamic = "force-dynamic";

export default async function ProductList() {
  const data = await fetch(`http://${process.env.NEXT_PUBLIC_API_URL}/api/v1/products`)
  const products: Product[] = await data.json()
  return <div className="flex w-screen justify-center align-middle items-center pt-16 px-4">
    <Card className="w-full">
      <CardHeader>
        <CardTitle>Produits</CardTitle>
      </CardHeader>
      <CardContent>
        <ProductTable products={products} />
      </CardContent>
    </Card>
  </div>
}
