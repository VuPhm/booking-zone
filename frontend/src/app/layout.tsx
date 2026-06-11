import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import Navbar from "@/components/shared/Navbar";
import { Toaster } from "sonner"; // Đảm bảo dùng Sonner chuẩn stack mới

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "BookingZone - Hệ thống đặt lịch salon",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="vi">
      <body className={inter.className}>
        <Navbar />
        <main className="mx-auto max-w-7xl px-4 py-6">{children}</main>
        <Toaster position="top-right" richColors />
      </body>
    </html>
  );
}
