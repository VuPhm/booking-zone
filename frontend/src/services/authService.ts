import { apiClient } from '@/lib/apiClient';
import { useAuthStore } from '@/store/authStore';
import { User } from '@/types';

interface AuthResponse {
  token: string;
  user: User;
}

export const authService = {
  async login(payload: Record<string, string>): Promise<AuthResponse> {
    const { data } = await apiClient.post<AuthResponse>('/auth/login', payload);
    useAuthStore.getState().setAuth(data.user, data.token);
    return data;
  },

  async register(payload: Record<string, string>): Promise<void> {
    await apiClient.post('/auth/register', payload);
  },
  
  async getMe(): Promise<User> {
    const { data } = await apiClient.get<User>('/auth/me');
    return data;
  }
};