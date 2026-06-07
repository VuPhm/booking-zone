"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import { useAuthStore } from "@/store/authStore";
import { Button } from "@/components/ui/button";
import { toast } from "sonner";

export default function Navbar() {
  const { user, logout } = useAuthStore();
  const router = useRouter();

  const handleLogout = () => {
    logout();
    toast.success("Đã đăng xuất tài khoản");
    router.push("/login");
  };

  return (
    <nav className="border-b bg-white backdrop-blur">
      <div className="mx-auto flex h-16 max-w-7xl items-center justify-between px-4">
        <Link href="/" className="text-xl font-bold text-primary">
          BookingZone 🎯
        </Link>

        <div className="flex items-center gap-4">
          <Link
            href="/services"
            className="text-sm font-medium hover:underline"
          >
            Dịch vụ
          </Link>

          {user ? (
            <>
              {user.role === "ADMIN" && (
                <Link
                  href="/admin"
                  className="text-sm font-medium text-destructive hover:underline"
                >
                  Quản trị
                </Link>
              )}
              <span className="text-sm text-muted-foreground">
                Chào, {user.fullName}
              </span>
              <Button variant="outline" size="sm" onClick={handleLogout}>
                Đăng xuất
              </Button>
            </>
          ) : (
            <div className="flex gap-2">
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
    </nav>
  );
}
