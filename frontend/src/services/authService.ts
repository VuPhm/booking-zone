import { apiClient } from '@/lib/apiClient';
import { useAuthStore } from '@/store/authStore';
import { User } from '@/types';

interface AuthResponse {
  token: string;
  user: User;
}

export const authService = {
async login(payload: Record<string, string>): Promise<any> {
    // 1. Gọi API nhận về object phẳng { fullName, token, role }
    const { data } = await apiClient.post('/auth/login', payload);
    
    // 2. Gom dữ liệu phẳng thành cấu trúc Object User đúng thiết kế hệ thống
    const userModel = {
      id: 1, // Gán id giả lập tạm thời nếu backend chưa trả về Id trong payload phẳng
      username: payload.username,
      fullName: data.fullName,
      role: data.role
    };

    // 3. Nạp chuẩn vào Store (Map biến "data.token" thay vì "data.user.token")
    useAuthStore.getState().setAuth(userModel, data.token);
    
    // 4. Trả về data chứa cấu trúc mới để trang login bóc tách quyền điều phối route
    return {
      token: data.token,
      user: userModel
    };
  },  
  async register(payload: Record<string, string>): Promise<void> {
    await apiClient.post('/auth/register', payload);
  },
  
  async getMe(): Promise<User> {
    const { data } = await apiClient.get<User>('/auth/me');
    return data;
  }
};