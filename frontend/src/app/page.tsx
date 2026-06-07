"use client";

import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { catalogService } from "@/services/catalogService";
import { ServiceCategory, ServiceItem } from "@/types";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { toast } from "sonner";

export default function HomePage() {
  const [categories, setCategories] = useState<ServiceCategory[]>([]);
  const [services, setServices] = useState<ServiceItem[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<number | undefined>(
    undefined,
  );
  const [loading, setLoading] = useState<boolean>(true);
  const router = useRouter();

  // Load danh mục bộ lọc ban đầu
  useEffect(() => {
    catalogService
      .getCategories()
      .then(setCategories)
      .catch(() => toast.error("Không thể tải danh mục ngành"));
  }, []);

  // Fetch danh sách gói dịch vụ phẳng dựa theo bộ lọc danh mục
  useEffect(() => {
    setLoading(true);
    catalogService
      .getServices(selectedCategory)
      .then(setServices)
      .catch(() => toast.error("Không thể tải danh sách dịch vụ"))
      .finally(() => setLoading(false));
  }, [selectedCategory]);

  return (
    <div className="space-y-8">
      {/* 🚀 Phần Hero giới thiệu */}
      <div className="text-center space-y-2 py-6">
        <h1 className="text-4xl font-extrabold tracking-tight">
          Hệ Thống Đặt Lịch Trực Tuyến
        </h1>
        <p className="text-muted-foreground text-lg">
          Chọn gói dịch vụ phù hợp và giữ chỗ làm đẹp ngay lập tức
        </p>
      </div>

      {/* 🏷️ Thanh bộ lọc danh mục (Tabs lọc phẳng) */}
      <div className="flex flex-wrap gap-2 justify-center border-b pb-4">
        <Button
          variant={selectedCategory === undefined ? "default" : "outline"}
          onClick={() => setSelectedCategory(undefined)}
          size="sm"
        >
          Tất cả dịch vụ
        </Button>
        {categories.map((cat) => (
          <Button
            key={cat.id}
            variant={selectedCategory === cat.id ? "default" : "outline"}
            onClick={() => setSelectedCategory(cat.id)}
            size="sm"
          >
            {cat.name}
          </Button>
        ))}
      </div>

      {/* 📦 Lưới hiển thị danh sách gói dịch vụ */}
      {loading ? (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {[1, 2, 3].map((n) => (
            <Card key={n} className="animate-pulse bg-muted/40 h-64 w-full" />
          ))}
        </div>
      ) : services.length === 0 ? (
        <div className="text-center py-12 text-muted-foreground">
          Cửa hàng hiện tại chưa cấu hình gói dịch vụ nào cho danh mục này.
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {services.map((item) => (
            <Card
              key={item.id}
              className="flex flex-col justify-between transition-all hover:shadow-md"
            >
              <CardHeader>
                <div className="flex justify-between items-start gap-2">
                  <CardTitle className="text-xl line-clamp-1">
                    {item.name}
                  </CardTitle>
                  <span className="text-xs bg-secondary text-secondary-foreground font-semibold px-2.5 py-0.5 rounded-full">
                    {item.category.name}
                  </span>
                </div>
                <CardDescription className="line-clamp-2 mt-2">
                  {item.description || "Không có mô tả."}
                </CardDescription>
              </CardHeader>
              <CardContent>
                <div className="flex justify-between items-center text-sm font-medium mt-4">
                  <span className="text-muted-foreground">⏱️ Thời lượng:</span>
                  <span>{item.duration} phút</span>
                </div>
                <div className="flex justify-between items-center text-lg font-bold text-primary mt-2">
                  <span>💰 Giá gói:</span>
                  <span>{item.price.toLocaleString("vi-VN")} đ</span>
                </div>
              </CardContent>
              <CardFooter>
                <Button
                  className="w-full font-semibold"
                  onClick={() => router.push(`/book/${item.id}`)} // Chuyển hướng thẳng sang form bốc ngày/giờ
                >
                  Đặt lịch ngay
                </Button>
              </CardFooter>{" "}
            </Card>
          ))}
        </div>
      )}
    </div>
  );
}
