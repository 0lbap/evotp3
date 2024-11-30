"use client";

import { Button } from "@/components/ui/button";
import { Calendar } from "@/components/ui/calendar";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover";
import { cn } from "@/lib/utils";
import { Label } from "@radix-ui/react-label";
import { format } from "date-fns";
import { CalendarIcon } from "lucide-react";
import Form from "next/form";
import { useEffect, useState } from "react";
import { createProduct } from "./actions";

export default function CreateProduct() {
  const [date, setDate] = useState<Date>()
  const [dateStr, setDateStr] = useState<string>()

  useEffect(() => {
    setDateStr(date?.toISOString())
  }, [date]);

  return <div className="flex w-screen h-screen justify-center items-center align-middle">
    <Form action={createProduct}>
      <Card className="mx-auto max-w-sm">
        <CardHeader>
          <CardTitle>Création d&apos;un produit</CardTitle>
          <CardDescription>Remplissez le formulaire afin de créer un produit</CardDescription>
        </CardHeader>
        <CardContent className="grid gap-2">
          <div className="grid gap-2">
            <Label htmlFor="name">Nom</Label>
            <Input id="name" name="name" />
          </div>
          <div className="grid gap-2">
            <Label htmlFor="price">Prix</Label>
            <Input id="price" name="price" type="number" />
          </div>
          <div className="grid gap-2">
            <Label htmlFor="price">Prix</Label>
            <Popover>
              <PopoverTrigger asChild>
                <Button
                  variant={"outline"}
                  className={cn(
                    "w-[280px] justify-start text-left font-normal",
                    !date && "text-muted-foreground"
                  )}
                >
                  <CalendarIcon />
                  {date ? format(date, "PPP") : <span>Choisissez une date</span>}
                </Button>
              </PopoverTrigger>
              <PopoverContent className="w-auto p-0">
                <Calendar
                  mode="single"
                  selected={date}
                  onSelect={setDate}
                  initialFocus
                />
              </PopoverContent>
            </Popover>
          </div>
          <input name="expirationDate" hidden value={dateStr} />
        </CardContent>
        <CardFooter>
          <Button type="submit" className="w-full">Enregister</Button>
        </CardFooter>
      </Card>
    </Form>
  </div>
}
