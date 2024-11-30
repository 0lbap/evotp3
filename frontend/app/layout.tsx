import AppSidebar from "@/components/sidebar/Sidebar";
import "./globals.css";
import { AuthProvider } from "@/hooks/use-auth";

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="fr">
      <body>
        <AuthProvider>
          <AppSidebar>
            {children}
          </AppSidebar>
        </AuthProvider>
      </body>
    </html>
  );
}
