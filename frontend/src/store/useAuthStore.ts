import { create } from 'zustand';
import { persist, createJSONStorage } from 'zustand/middleware';
import Cookies from 'js-cookie';
import { UserProfile } from '@/types/auth';

interface AuthState {
  token: string | null;
  user: UserProfile | null;
  isAuthenticated: boolean;
  setAuth: (accessToken: string, fullName: string, role: 'CUSTOMER' | 'ADMIN') => void;
  clearAuth: () => void;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      token: null,
      user: null,
      isAuthenticated: false,
      setAuth: (accessToken, fullName, role) => {
        // Ghi token vào cookie để Middleware Next.js ở Server đọc được lập tức
        Cookies.set('auth-token', accessToken, { expires: 7 }); // Hạn 7 ngày
        Cookies.set('auth-role', role, { expires: 7 });

        set({ 
          token: accessToken, 
          user: { username: '', fullName, role }, 
          isAuthenticated: true 
        });
      },
      clearAuth: () => {
        // Xóa sạch cookie khi đăng xuất
        Cookies.remove('auth-token');
        Cookies.remove('auth-role');

        set({ token: null, user: null, isAuthenticated: false });
      },
    }),
    {
      name: 'auth-storage',
      storage: createJSONStorage(() => localStorage),
    }
  )
);