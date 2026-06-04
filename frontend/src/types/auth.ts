export interface UserProfile {
  id: number;
  email: string;
  fullName: string;
  role: 'USER' | 'ADMIN'; // Phù hợp với phân quyền .hasRole("ADMIN") ở backend
}

export interface AuthResponse {
  accessToken: string;
  tokenType: string;
  user: UserProfile;
}