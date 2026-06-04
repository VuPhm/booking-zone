import type { Metadata } from "next";
import { Inter, Geist } from "next/font/google";
import "./globals.css";
import { Navbar } from "@/components/shared/Navbar";
import { Sonner } from "@/components/ui/sonner";
import { cn } from "@/lib/utils";

const geist = Geist({ subsets: ["latin"], variable: "--font-sans" });

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "BookingZone - Hệ thống đặt lịch trực tuyến",
  description: "Ứng dụng đặt lịch nhanh chóng, tiện lợi",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="vi" className={cn("font-sans", geist.variable)}>
      <body className={inter.className}>
        <div className="relative flex min-h-screen flex-col">
          {/* Thanh công cụ xuyên suốt ứng dụng */}
          <Navbar />

          {/* Nội dung thay đổi theo từng trang */}
          <main className="flex-1">{children}</main>
        </div>
        <Sonner />
      </body>
    </html>
  );
}
