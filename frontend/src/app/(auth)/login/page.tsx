"use client";

import { useRouter } from "next/navigation";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { toast } from "sonner";

import { authService } from "@/services/authService";
import { useAuthStore } from "@/store/useAuthStore";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import Link from "next/link";

// 1. Định nghĩa Schema Validation chuyển email thành username
const loginSchema = z.object({
  username: z.string().min(1, { message: "Tên tài khoản không được để trống" }),
  password: z.string().min(1, { message: "Mật khẩu không được để trống" }),
});

export default function LoginPage() {
  const router = useRouter();
  const setAuth = useAuthStore((state) => state.setAuth);

  const form = useForm<z.infer<typeof loginSchema>>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      username: "",
      password: "",
    },
  });

  const onSubmit = async (values: z.infer<typeof loginSchema>) => {
    try {
      // 1. Gọi API đăng nhập và nhận về Flat Payload
      const res = await authService.login(values);

      // 2. Truyền trực tiếp các trường phẳng vào Zustand Store
      setAuth(res.accessToken, res.fullName, res.role);

      toast.success(`Chào mừng trở lại, ${res.fullName}!`);

      // 3. Điều hướng dựa trên role chính xác từ API ("ADMIN" hoặc "CUSTOMER")
      if (res.role === "ADMIN") {
        router.push("/admin");
      } else {
        router.push("/");
      }
    } catch (error: any) {
      console.error("Login error:", error);
    }
  };

  return (
    <div className="flex min-h-[calc(100vh-4rem)] items-center justify-center p-6 bg-muted/40">
      <Card className="w-full max-w-md shadow-lg">
        <CardHeader className="space-y-1 text-center">
          <CardTitle className="text-2xl font-bold tracking-tight">
            Đăng nhập
          </CardTitle>
          <CardDescription>
            Nhập tài khoản và mật khẩu của bạn để truy cập hệ thống
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
              <FormField
                control={form.control}
                name="username"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Tên tài khoản (Username)</FormLabel>
                    <FormControl>
                      <Input placeholder="vup_admin" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="password"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Mật khẩu</FormLabel>
                    <FormControl>
                      <Input
                        type="password"
                        placeholder="••••••••"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <Button
                type="submit"
                className="w-full font-semibold mt-2"
                disabled={form.formState.isSubmitting}
              >
                {form.formState.isSubmitting ? "Đang xác thực..." : "Đăng nhập"}
              </Button>
            </form>
          </Form>

          <div className="mt-4 text-center text-sm text-muted-foreground">
            Chưa có tài khoản?{" "}
            <Link
              href="/register"
              className="text-primary underline underline-offset-4 hover:opacity-80"
            >
              Đăng ký thành viên
            </Link>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
