"use client";

import { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import { catalogService } from "@/services/catalogService";
import { bookingService } from "@/services/bookingService";
import { useAuthStore } from "@/store/authStore";
import { ServiceItem, TimeSlot } from "@/types";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { toast } from "sonner";
import { apiClient } from "@/lib/apiClient";

export default function BookServicePage() {
  const { id } = useParams();
  const router = useRouter();
  const { user } = useAuthStore();

  const [service, setService] = useState<ServiceItem | null>(null);
  const [slots, setSlots] = useState<TimeSlot[]>([]);
  const [selectedDate, setSelectedDate] = useState<string>("");
  const [selectedSlot, setSelectedSlot] = useState<number | null>(null);
  const [submitting, setSubmitting] = useState(false);

  // 1. Tải thông tin chi tiết gói dịch vụ phẳng đã chọn
  useEffect(() => {
    catalogService.getServices().then((res) => {
      const found = res.find((s) => s.id === Number(id));
      if (found) setService(found);
    });

    // 2. Tải danh sách khung giờ trống của cửa hàng
    apiClient.get("/slots/available").then((res) => setSlots(res.data));
  }, [id]);

  const handleBookingSubmit = async () => {
    if (!user) {
      toast.error("Bạn cần đăng nhập tài khoản thành viên để đặt lịch!");
      return router.push("/login");
    }
    if (!selectedDate || !selectedSlot) {
      return toast.warning("Vui lòng điền đủ Ngày và chọn Giờ hẹn!");
    }

    try {
      setSubmitting(true);
      await bookingService.createBooking({
        bookingDate: selectedDate,
        serviceId: Number(id),
        timeSlotId: selectedSlot,
        userId: user.id,
      });
      toast.success("Đặt lịch hẹn thành công mĩ mãn!");
      router.push("/");
    } catch (error: any) {
      toast.error(
        error.response?.data?.message ||
          "Lỗi trùng lịch, vui lòng chọn giờ khác!",
      );
    } finally {
      setSubmitting(false);
    }
  };

  if (!service)
    return (
      <div className="text-center py-12">Đang tải thông tin dịch vụ...</div>
    );

  return (
    <div className="max-w-3xl mx-auto space-y-6">
      <Card>
        <CardHeader>
          <CardTitle>Xác nhận lịch đặt: {service.name}</CardTitle>
          <CardDescription>
            Thời lượng: {service.duration} phút — Chi phí:{" "}
            {service.price.toLocaleString()} đ
          </CardDescription>
        </CardHeader>
        <CardContent className="space-y-4">
          {/* Chọn ngày thủ công/ô input date gọn gàng */}
          <div className="space-y-2">
            <label className="text-sm font-semibold">1. Chọn Ngày Hẹn:</label>
            <input
              type="date"
              className="w-full border rounded-md p-2 bg-white"
              onChange={(e) => setSelectedDate(e.target.value)}
              min={new Date().toISOString().split("T")[0]}
            />
          </div>

          {/* Lưới chọn Giờ làm việc phẳng đổ ra từ backend */}
          <div className="space-y-2">
            <label className="text-sm font-semibold">
              2. Chọn Khung Giờ Làm Việc Còn Trống:
            </label>
            <div className="grid grid-cols-3 gap-2">
              {slots.map((slot) => (
                <Button
                  key={slot.id}
                  type="button"
                  variant={selectedSlot === slot.id ? "default" : "outline"}
                  onClick={() => setSelectedSlot(slot.id)}
                  className="text-xs"
                >
                  {slot.startTime.substring(0, 5)} -{" "}
                  {slot.endTime.substring(0, 5)}
                </Button>
              ))}
            </div>
          </div>

          <Button
            className="w-full mt-6 bg-primary text-lg h-12"
            onClick={handleBookingSubmit}
            disabled={submitting}
          >
            {submitting
              ? "Đang gửi yêu cầu giữ chỗ..."
              : "Hoàn Tất Đặt Lịch ✔️"}
          </Button>
        </CardContent>
      </Card>
    </div>
  );
}
