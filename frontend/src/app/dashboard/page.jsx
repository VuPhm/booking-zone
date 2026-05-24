'use client';

import { useAuthStore } from '@/store/useAuthStore';
import { useRouter } from 'next/navigation';
import apiClient from '@/lib/axiosClient';
import { useState, useEffect } from 'react';

export default function DashboardPage() {
  const { user, clearAuth } = useAuthStore();
  const router = useRouter();
  const [services, setServices] = useState([]);
  const [error, setError] = useState('');

  // Bảo vệ route ở tầng Client: Chưa đăng nhập thì đá về login
  useEffect(() => {
    if (!user) router.push('/login');
  }, [user, router]);

  const fetchProtectedData = async () => {
    setError('');
    try {
      // Request này tự động được Axios Interceptor đính thêm Bearer Token ở Header
      const res = await apiClient.get('/services');
      setServices(res.data);
    } catch (err) {
      setError('Không thể lấy dữ liệu. Token không hợp lệ hoặc hết hạn!');
    }
  };

  const handleLogout = async () => {
    try {
      await apiClient.post('/auth/logout');
    } catch (e) {
      // Vẫn clear store kể cả khi API logout lỗi kết nối
    } finally {
      clearAuth();
      router.push('/login');
    }
  };

  if (!user) return null;

  return (
    <div className="p-6 max-w-4xl mx-auto">
      <div className="flex justify-between items-center bg-white p-4 rounded shadow mb-6">
        <div>
          <h1 className="text-xl font-bold text-gray-800">Hệ Thống Đặt Lịch Dịch Vụ</h1>
          <p className="text-sm text-gray-500">Xin chào công việc: <span className="font-semibold text-blue-600">{user.email}</span></p>
        </div>
        <button onClick={handleLogout} className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded text-sm font-medium">
          Đăng xuất
        </button>
      </div>

      <div className="bg-white p-6 rounded shadow">
        <div className="flex items-center justify-between mb-4">
          <h3 className="text-lg font-bold text-gray-700">Dịch Vụ Từ Hệ Thống (Yêu cầu Đăng nhập)</h3>
          <button onClick={fetchProtectedData} className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded text-sm">
            Tải Dịch Vụ (Gọi API Bảo Mật)
          </button>
        </div>

        {error && <div className="p-3 bg-red-100 text-red-700 rounded text-sm mb-4">{error}</div>}

        {services.length > 0 ? (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            {services.map((item) => (
              <div key={item.id} className="p-4 border border-gray-200 rounded hover:border-blue-300 transition">
                <h4 className="font-bold text-gray-800">{item.name}</h4>
                <p className="text-blue-600 font-semibold mt-1">{item.price.toLocaleString('vi-VN')} VND</p>
              </div>
            ))}
          </div>
        ) : (
          <p className="text-gray-400 text-center py-8 border border-dashed border-gray-200 rounded">
            Chưa có dữ liệu. Nhấn nút phía trên để kiểm tra Token Auth.
          </p>
        )}
      </div>
    </div>
  );
}