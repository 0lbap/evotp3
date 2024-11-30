import { Sidebar, SidebarContent, SidebarHeader, SidebarProvider, SidebarTrigger } from "@/components/ui/sidebar";
import { Package } from "lucide-react";
import { PropsWithChildren } from "react";
import AuthGroup from "./AuthGroup";
import AppGroup from "./AppGroup";

export default function AppSidebar({ children }: PropsWithChildren) {
  return <SidebarProvider>
    <Sidebar variant="floating" className="">
      <SidebarHeader className="flex-row align-middle items-center p-4">
        <Package className="bg-primary text-primary-foreground rounded p-1" />
        <span className="font-bold">Evo TP 1</span>
      </SidebarHeader>
      <SidebarContent>
        <AuthGroup />
        <AppGroup />
      </SidebarContent>
    </Sidebar>
    <main>
      <SidebarTrigger className="fixed m-4" />
      {children}
    </main>
  </SidebarProvider>
}
