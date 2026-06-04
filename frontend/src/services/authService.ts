import { apiClient } from '@/lib/apiClient';
import { AuthResponse, UserProfile } from '@/types/auth';

export const authService = {
  // Đăng nhập hệ thống
  login: async (data: any): Promise<AuthResponse> => {
    const response = await apiClient.post<AuthResponse>('/auth/login', data);
    return response.data;
  },

  // Đăng ký tài khoản mới
  register: async (data: any): Promise<void> => {
    await apiClient.post('/auth/register', data);
  },

  // Lấy thông tin user hiện tại (Dùng kiểm tra token còn sống hay không)
  getProfile: async (): Promise<UserProfile> => {
    const response = await apiClient.get<UserProfile>('/auth/me');
    return response.data;
  },

  // Đăng xuất ở phía Backend nếu cần
  logout: async (): Promise<void> => {
    await apiClient.post('/auth/logout');
  }
};