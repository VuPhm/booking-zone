import { create } from 'zustand';

export const useAuthStore = create((set) => ({
  accessToken: null,
  user: null, // Chứa thông tin giải mã từ token như email, role khi cần
  isInitialized: false, // Đánh dấu đã kiểm tra xong trạng thái phục hồi phiên khi F5

  setAuth: (accessToken, user) => set({ accessToken, user, isInitialized: true }),
  
  clearAuth: () => set({ accessToken: null, user: null, isInitialized: true }),
  
  setInitialized: (status) => set({ isInitialized: status })
}));