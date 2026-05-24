'use client';

import { useForm } from 'react-hook-form';
import { useRouter } from 'next/navigation';
import apiClient from '@/lib/axiosClient';
import { useAuthStore } from '@/store/useAuthStore';
import { useState } from 'react';

export default function LoginPage() {
  const { register, handleSubmit, formState: { errors } } = useForm();
  const { setAuth } = useAuthStore();
  const router = useRouter();
  const [apiError, setApiError] = useState('');

  const onSubmit = async (data) => {
    setApiError('');
    try {
      // Gọi API đăng nhập, Cookie refresh_token tự động được trình duyệt lưu qua set-cookie
      const res = await apiClient.post('/auth/login', data);
      const { accessToken } = res.data;

      // Lưu Access Token vào RAM của hệ thống qua Zustand
      setAuth(accessToken, { email: data.email });
      
      // Chuyển hướng sang trang Dashboard bảo mật
      router.push('/dashboard');
    } catch (error) {
      setApiError(error.response?.data || 'Đăng nhập thất bại. Vui lòng kiểm tra lại!');
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
      <div className="max-w-md w-full bg-white rounded-lg shadow-md p-8">
        <h2 className="text-2xl font-bold text-center text-gray-800 mb-6">ĐĂNG NHẬP HỆ THỐNG</h2>
        
        {apiError && <div className="mb-4 p-3 bg-red-100 text-red-700 text-sm rounded">{apiError}</div>}

        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
            <input
              type="email"
              className={`w-full p-2 border rounded focus:outline-none focus:ring-2 ${errors.email ? 'border-red-500 focus:ring-red-200' : 'border-gray-300 focus:ring-blue-200'}`}
              {...register('email', { required: 'Email không được để trống' })}
            />
            {errors.email && <span className="text-red-500 text-xs mt-1 block">{errors.email.message}</span>}
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Mật khẩu</label>
            <input
              type="password"
              className={`w-full p-2 border rounded focus:outline-none focus:ring-2 ${errors.password ? 'border-red-500 focus:ring-red-200' : 'border-gray-300 focus:ring-blue-200'}`}
              {...register('password', { required: 'Mật khẩu không được để trống' })}
            />
            {errors.password && <span className="text-red-500 text-xs mt-1 block">{errors.password.message}</span>}
          </div>

          <button type="submit" className="w-full bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 rounded transition">
            Đăng nhập
          </button>
        </form>
      </div>
    </div>
  );
}