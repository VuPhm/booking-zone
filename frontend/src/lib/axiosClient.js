import axios from 'axios';
import { useAuthStore } from '@/store/useAuthStore';

const apiClient = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_URL,
  withCredentials: true, // BẮT BUỘC: Cho phép trình duyệt tự gửi/nhận HTTP-Only Cookie (Refresh Token)
});

// Interceptor cho REQUEST: Tự động gắn Bearer Token vào Header nếu có
apiClient.interceptors.request.use(
  (config) => {
    const token = useAuthStore.getState().accessToken;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Interceptor cho RESPONSE: Đón đầu lỗi 401 để tự động làm mới phiên
apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    // Nếu gặp lỗi 401 (Hết hạn Access Token) và request này chưa từng thử refresh trước đó
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true; // Đánh dấu để tránh vòng lặp vô hạn nếu refresh cũng lỗi

      try {
        // Gọi API Refresh. Spring Boot sẽ tự đọc Refresh Token từ HttpOnly Cookie
        const res = await axios.post(
          `${process.env.NEXT_PUBLIC_API_URL}/auth/refresh`,
          {},
          { withCredentials: true }
        );

        const { accessToken } = res.data;

        // Cập nhật Access Token mới vào Zustand Store
        useAuthStore.getState().setAuth(accessToken, useAuthStore.getState().user);

        // Đính lại token mới vào request cũ bị lỗi và thực thi lại nó
        originalRequest.headers.Authorization = `Bearer ${accessToken}`;
        return apiClient(originalRequest);
      } catch (refreshError) {
        // Nếu gọi API Refresh cũng thất bại (Refresh Token hết hạn 7 ngày) -> Ép cút về Login
        useAuthStore.getState().clearAuth();
        if (typeof window !== 'undefined') {
          window.location.href = '/login';
        }
        return Promise.reject(refreshError);
      }
    }

    return Promise.reject(error);
  }
);

export default apiClient;