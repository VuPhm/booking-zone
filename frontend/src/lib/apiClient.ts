import axios from 'axios';

// Lấy Base URL từ biến môi trường
const baseURL = process.env.NEXT_PUBLIC_API_URL;

export const apiClient = axios.create({
  baseURL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor xử lý TRƯỚC KHI gửi request lên Backend
apiClient.interceptors.request.use(
  (config) => {
    // Chỉ chạy ở môi trường Client (Trình duyệt) để tránh lỗi SSR của Next.js
    if (typeof window !== 'undefined') {
      const authStorage = localStorage.getItem('auth-storage');
      if (authStorage) {
        try {
          const parsed = JSON.parse(authStorage);
          const token = parsed?.state?.token;
          if (token) {
            config.headers.Authorization = `Bearer ${token}`;
          }
        } catch (error) {
          console.error("Lỗi parse auth-storage từ localStorage:", error);
        }
      }
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Interceptor xử lý SAU KHI nhận response từ Backend (Bắt lỗi 401, 403)
apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && (error.response.status === 401 || error.response.status === 403)) {
      // Nếu token hết hạn hoặc không hợp lệ, xóa dữ liệu và đá về trang login
      if (typeof window !== 'undefined') {
        localStorage.removeItem('auth-storage');
        window.location.href = '/login';
      }
    }
    return Promise.reject(error);
  }
);