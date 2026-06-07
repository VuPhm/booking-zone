export interface UserProfile {
  username: string; // Giữ để Navbar hoặc các logic khác dùng nếu cần
  fullName: string;
  role: 'CUSTOMER' | 'ADMIN' | 'PROVIDER'; // Cập nhật đúng role 'CUSTOMER' từ API của bạn
}

export interface AuthResponse {
  accessToken: string;
  fullName: string;
  role: 'CUSTOMER' | 'ADMIN' | 'PROVIDER';
}