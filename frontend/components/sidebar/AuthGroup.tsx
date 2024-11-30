"use client";

import { SidebarGroup, SidebarGroupContent, SidebarGroupLabel, SidebarMenuButton, SidebarMenuItem } from "@/components/ui/sidebar";
import { useAuth } from "@/hooks/use-auth";
import { UserPlus, UserSearch } from "lucide-react";
import { usePathname } from "next/navigation";

export default function AuthGroup() {
  const pathname = usePathname()
  const { isLoggedIn } = useAuth()
  if (isLoggedIn)
    return <></>

  return <SidebarGroup>
    <SidebarGroupLabel>Authentification</SidebarGroupLabel>
    <SidebarGroupContent>
      <SidebarMenuItem>
        <SidebarMenuButton isActive={pathname === "/auth/sign-up"} asChild>
          <a href="/auth/sign-up">
            <UserPlus />
            <span>Inscription</span>
          </a>
        </SidebarMenuButton>
        <SidebarMenuButton isActive={pathname === "/auth/sign-in"} asChild>
          <a href="/auth/sign-in">
            <UserSearch />
            <span>Connexion</span>
          </a>
        </SidebarMenuButton>
      </SidebarMenuItem>
    </SidebarGroupContent>
  </SidebarGroup>
}
