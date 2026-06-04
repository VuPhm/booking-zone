"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import { useAuthStore } from "@/store/useAuthStore";
import { authService } from "@/services/authService";
import { Button } from "@/components/ui/button";
import { toast } from "sonner"; // 👈 Import trực tiếp 'toast' từ thư viện sonner

export function Navbar() {
  const { user, isAuthenticated, clearAuth } = useAuthStore();
  const router = useRouter();

  const handleLogout = async () => {
    try {
      await authService.logout();
    } catch (error) {
      console.error("Backend logout error:", error);
    } finally {
      clearAuth();
      // 👈 Cú pháp gọi thông báo thành công siêu gọn của Sonner
      toast.success("Đăng xuất thành công!", {
        description: "Hẹn gặp lại bạn lần sau.",
      });
      router.push("/login");
    }
  };

  return (
    <header className="sticky top-0 z-50 w-full border-b bg-background/95 backdrop-blur supports-backdrop-filter:bg-background/60">
      <div className="container flex h-16 items-center justify-between px-6">
        {/* Logo dự án */}
        <Link
          href="/"
          className="flex items-center space-x-2 font-bold text-xl tracking-tight text-primary"
        >
          Booking<span>Zone</span>
        </Link>

        {/* Menu điều hướng chính */}
        <nav className="flex items-center space-x-6 text-sm font-medium">
          <Link
            href="/"
            className="transition-colors hover:text-foreground/80 text-foreground"
          >
            Trang chủ
          </Link>
          <Link
            href="/services"
            className="transition-colors hover:text-foreground/80 text-foreground"
          >
            Dịch vụ
          </Link>

          {/* Menu đặc quyền dành riêng cho ADMIN */}
          {isAuthenticated && user?.role === "ADMIN" && (
            <Link
              href="/admin"
              className="text-destructive font-semibold transition-colors hover:opacity-80"
            >
              Trang Quản Trị
            </Link>
          )}
        </nav>

        {/* Khối xử lý trạng thái Đăng nhập / Đăng xuất */}
        <div className="flex items-center space-x-4">
          {isAuthenticated && user ? (
            <div className="flex items-center space-x-4">
              <span className="text-sm font-medium text-muted-foreground">
                Xin chào,{" "}
                <span className="text-foreground font-semibold">
                  {user.fullName}
                </span>
              </span>
              <Button variant="outline" size="sm" onClick={handleLogout}>
                Đăng xuất
              </Button>
            </div>
          ) : (
            <div className="flex items-center space-x-2">
              <Button variant="ghost" size="sm" asChild>
                <Link href="/login">Đăng nhập</Link>
              </Button>
              <Button size="sm" asChild>
                <Link href="/register">Đăng ký</Link>
              </Button>
            </div>
          )}
        </div>
      </div>
    </header>
  );
}
