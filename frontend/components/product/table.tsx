import { Table, TableBody, TableCaption, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { Product } from "@/lib/types";
import { Button } from "../ui/button";
import { Trash } from "lucide-react";
import Form from "next/form";
import { deleteProduct } from "@/app/product/create/actions";

type Props = {
  products: Product[]
}

export default function ProductTable({ products }: Props) {
  return <Table>
    <TableCaption>La liste des produits disponibles au sein de l&apos;application.</TableCaption>
    <TableHeader>
      <TableRow>
        <TableHead>ID</TableHead>
        <TableHead>Nom</TableHead>
        <TableHead>Prix</TableHead>
        <TableHead>Date d&apos;expiration</TableHead>
        <TableHead>Actions</TableHead>
      </TableRow>
    </TableHeader>
    <TableBody>
      {products.map(p =>
        <TableRow key={p.id}>
          <TableCell>{p.id}</TableCell>
          <TableCell>{p.name}</TableCell>
          <TableCell>{p.price}</TableCell>
          <TableCell>{p.expirationDate}</TableCell>
          <TableCell>
            <Form action={deleteProduct}>
              <input name="id" value={p.id} hidden />
              <Button variant="destructive">
                <Trash /> Supprimer
              </Button>
            </Form>
          </TableCell>
        </TableRow>
      )}
    </TableBody >
  </Table>
}
