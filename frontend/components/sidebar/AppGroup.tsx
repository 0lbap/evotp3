"use client";

import { SidebarGroup, SidebarGroupContent, SidebarGroupLabel, SidebarMenuButton, SidebarMenuItem } from "@/components/ui/sidebar";
import { useAuth } from "@/hooks/use-auth";
import { PackagePlus, PackageSearch } from "lucide-react";
import { usePathname } from "next/navigation";

export default function AppGroup() {
  const pathname = usePathname()
  const { isLoggedIn } = useAuth()
  return <SidebarGroup>
    <SidebarGroupLabel>Application</SidebarGroupLabel>
    <SidebarGroupContent>
      <SidebarMenuItem>
        <SidebarMenuButton isActive={pathname === "/product"} asChild>
          <a href="/product">
            <PackageSearch />
            <span>Produits</span>
          </a>
        </SidebarMenuButton>
        {isLoggedIn && <SidebarMenuButton isActive={pathname === "/product/create"} disabled={!isLoggedIn} asChild>
          <a href="/product/create">
            <PackagePlus />
            <span>Créer un produit</span>
          </a>
        </SidebarMenuButton>}
      </SidebarMenuItem>
    </SidebarGroupContent>
  </SidebarGroup>
}


