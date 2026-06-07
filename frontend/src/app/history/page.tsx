"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { bookingService } from "@/services/bookingService";
import { useAuthStore } from "@/store/authStore";
import { Booking } from "@/types";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";
import { toast } from "sonner";

export default function BookingHistoryPage() {
  const { user } = useAuthStore();
  const router = useRouter();
  const [history, setHistory] = useState<Booking[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!user) {
      toast.error("Vui lòng đăng nhập để xem lịch sử đặt lịch");
      return router.push("/login");
    }

    bookingService
      .getMyHistory(user.id)
      .then(setHistory)
      .catch(() => toast.error("Không thể tải lịch sử lịch hẹn"))
      .finally(() => setLoading(false));
  }, [user, router]);

  const getStatusBadge = (status: Booking["status"]) => {
    switch (status) {
      case "CONFIRMED":
        return <Badge className="bg-green-600">Thành công</Badge>;
      case "PENDING":
        return (
          <Badge
            variant="outline"
            className="text-yellow-600 border-yellow-500"
          >
            Chờ xử lý
          </Badge>
        );
      case "CANCELLED":
        return <Badge variant="destructive">Đã hủy</Badge>;
      default:
        return <Badge variant="secondary">{status}</Badge>;
    }
  };

  if (loading)
    return (
      <div className="text-center py-12">Đang tải lịch sử lịch hẹn...</div>
    );

  return (
    <div className="max-w-4xl mx-auto space-y-6">
      <Card>
        <CardHeader>
          <CardTitle className="text-2xl">Lịch sử hẹn của bạn 🗓️</CardTitle>
          <CardDescription>
            Danh sách các gói dịch vụ bạn đã đăng ký tại cửa hàng
          </CardDescription>
        </CardHeader>
        <CardContent>
          {history.length === 0 ? (
            <div className="text-center py-8 text-muted-foreground">
              Bạn chưa thực hiện bất kỳ lịch đặt nào trên hệ thống.
            </div>
          ) : (
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Mã đơn</TableHead>
                  <TableHead>Gói dịch vụ</TableHead>
                  <TableHead>Ngày hẹn</TableHead>
                  <TableHead>Khung giờ</TableHead>
                  <TableHead>Chi phí</TableHead>
                  <TableHead className="text-right">Trạng thái</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {history.map((booking) => (
                  <TableRow key={booking.id}>
                    <TableCell className="font-medium">
                      #BZ-{booking.id}
                    </TableCell>
                    <TableCell>{booking.service.name}</TableCell>
                    <TableCell>{booking.bookingDate}</TableCell>
                    <TableCell>
                      {booking.timeSlot.startTime.substring(0, 5)} -{" "}
                      {booking.timeSlot.endTime.substring(0, 5)}
                    </TableCell>
                    <TableCell>
                      {booking.totalPrice.toLocaleString("vi-VN")} đ
                    </TableCell>
                    <TableCell className="text-right">
                      {getStatusBadge(booking.status)}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          )}
        </CardContent>
      </Card>
    </div>
  );
}
