"use client";

import { useRouter } from "next/navigation";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { toast } from "sonner";

import { authService } from "@/services/authService";
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

// 1. Schema Validation chuyển email thành username
const registerSchema = z
  .object({
    fullName: z
      .string()
      .min(2, { message: "Họ và tên phải có ít nhất 2 ký tự" }),
    username: z
      .string()
      .min(3, { message: "Tên tài khoản phải có ít nhất 3 ký tự" })
      .max(20, { message: "Tên tài khoản tối đa 20 ký tự" })
      .regex(/^[a-zA-Z0-9_]+$/, {
        message: "Tên tài khoản không chứa khoảng trắng hoặc ký tự đặc biệt",
      }),
    password: z
      .string()
      .min(6, { message: "Mật khẩu phải có ít nhất 6 ký tự" }),
    confirmPassword: z.string(),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "Mật khẩu xác nhận không trùng khớp",
    path: ["confirmPassword"],
  });

export default function RegisterPage() {
  const router = useRouter();

  const form = useForm<z.infer<typeof registerSchema>>({
    resolver: zodResolver(registerSchema),
    defaultValues: {
      fullName: "",
      username: "",
      password: "",
      confirmPassword: "",
    },
  });

  const onSubmit = async (values: z.infer<typeof registerSchema>) => {
    try {
      await authService.register({
        fullName: values.fullName,
        username: values.username, // Gửi username lên Spring Boot
        password: values.password,
      });

      toast.success("Đăng ký tài khoản thành công! Hãy đăng nhập.");
      router.push("/login");
    } catch (error: any) {
      console.error("Register error:", error);
    }
  };

  return (
    <div className="flex min-h-[calc(100vh-4rem)] items-center justify-center p-6 bg-muted/40">
      <Card className="w-full max-w-md shadow-lg">
        <CardHeader className="space-y-1 text-center">
          <CardTitle className="text-2xl font-bold tracking-tight">
            Tạo tài khoản
          </CardTitle>
          <CardDescription>
            Nhập thông tin của bạn để đăng ký thành viên BookingZone
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Form {...form}>
            <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
              <FormField
                control={form.control}
                name="fullName"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Họ và tên</FormLabel>
                    <FormControl>
                      <Input placeholder="Nguyen Van A" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

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

              <FormField
                control={form.control}
                name="confirmPassword"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Xác nhận mật khẩu</FormLabel>
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
                {form.formState.isSubmitting ? "Đang xử lý..." : "Đăng ký"}
              </Button>
            </form>
          </Form>

          <div className="mt-4 text-center text-sm text-muted-foreground">
            Đã có tài khoản?{" "}
            <Link
              href="/login"
              className="text-primary underline underline-offset-4 hover:opacity-80"
            >
              Đăng nhập ngay
            </Link>
          </div>
        </CardContent>
      </Card>
    </div>
  );
}
