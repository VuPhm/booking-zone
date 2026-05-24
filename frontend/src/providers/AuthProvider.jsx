'use client';

import { useEffect } from 'react';
import axios from 'axios';
import { useAuthStore } from '@/store/useAuthStore';

export default function AuthProvider({ children }) {
  const { setAuth, clearAuth, isInitialized } = useAuthStore();

  useEffect(() => {
    const initializeAuth = async () => {
      try {
        // Vừa tải trang, lập tức gọi ngầm API Refresh để lấy Access Token mới qua Cookie
        const res = await axios.post(
          `${process.env.NEXT_PUBLIC_API_URL}/auth/refresh`,
          {},
          { withCredentials: true }
        );
        const { accessToken } = res.data;
        
        // Mock thông tin user tạm thời từ email (hoặc bạn có thể giải mã JWT tại đây)
        setAuth(accessToken, { email: "user@test.com" });
      } catch (error) {
        // Không có Cookie hoặc Cookie hết hạn -> Đánh dấu clear để render ứng dụng dạng Guest
        clearAuth();
      }
    };

    initializeAuth();
  }, [setAuth, clearAuth]);

  // Tránh hiện tượng nhấp nháy giao diện (Flickering) khi chưa check xong trạng thái login
  if (!isInitialized) {
    return <div className="h-screen w-screen flex items-center justify-center bg-gray-50">Loading session...</div>;
  }

  return <>{children}</>;
}