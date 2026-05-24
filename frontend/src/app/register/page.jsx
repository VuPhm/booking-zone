'use client';

import { useForm } from 'react-hook-form';
import { useRouter } from 'next/navigation';
import apiClient from '@/lib/axiosClient';
import { useState } from 'react';

export default function RegisterPage() {
  const { register, handleSubmit, formState: { errors }, watch } = useForm();
  const router = useRouter();
  const [msg, setMsg] = useState({ type: '', text: '' });

  const onSubmit = async (data) => {
    try {
      await apiClient.post('/auth/register', { email: data.email, password: data.password });
      setMsg({ type: 'success', text: 'Đăng ký thành công! Đang chuyển hướng...' });
      setTimeout(() => router.push('/login'), 2000);
    } catch (error) {
      setMsg({ type: 'error', text: error.response?.data || 'Đăng ký thất bại!' });
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100 px-4">
      <div className="max-w-md w-full bg-white rounded-lg shadow-md p-8">
        <h2 className="text-2xl font-bold text-center text-gray-800 mb-6">ĐĂNG KÝ TÀI KHOẢN</h2>
        
        {msg.text && (
          <div className={`mb-4 p-3 text-sm rounded ${msg.type === 'success' ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'}`}>
            {msg.text}
          </div>
        )}

        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
            <input
              type="email"
              className="w-full p-2 border border-gray-300 rounded"
              {...register('email', { required: 'Email không được trống' })}
            />
            {errors.email && <span className="text-red-500 text-xs block">{errors.email.message}</span>}
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Mật khẩu</label>
            <input
              type="password"
              className="w-full p-2 border border-gray-300 rounded"
              {...register('password', { required: 'Mật khẩu không được trống', minLength: { value: 6, message: 'Tối thiểu 6 ký tự' } })}
            />
            {errors.password && <span className="text-red-500 text-xs block">{errors.password.message}</span>}
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Xác nhận mật khẩu</label>
            <input
              type="password"
              className="w-full p-2 border border-gray-300 rounded"
              {...register('confirmPassword', { 
                required: 'Vui lòng xác nhận mật khẩu',
                validate: (value) => value === watch('password') || 'Mật khẩu xác nhận không trùng khớp'
              })}
            />
            {errors.confirmPassword && <span className="text-red-500 text-xs block">{errors.confirmPassword.message}</span>}
          </div>

          <button type="submit" className="w-full bg-green-600 hover:bg-green-700 text-white font-medium py-2 rounded transition">
            Đăng ký
          </button>
        </form>
      </div>
    </div>
  );
}