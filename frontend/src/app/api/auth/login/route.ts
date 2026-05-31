import { NextResponse } from 'next/server';
import { cookies } from 'next/headers';

export async function POST(request: Request) {
  try {
    const body = await request.json();

    // 1. Forward request sang Spring Boot Backend
    const backendRes = await fetch('http://localhost:8080/api/v1/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    });

    // 2. Kiểm tra nếu Backend trả ra trạng thái trống (No Content)
    if (backendRes.status === 204) {
      return NextResponse.json(
        { status: 400, error: 'Bad Request', errors: { global: 'Không có dữ liệu trả về từ hệ thống gốc' } },
        { status: 400 }
      );
    }

    // Đọc text thô trước để tránh crash dữ liệu JSON
    const resText = await backendRes.text();
    let resData;
    try {
      resData = resText ? JSON.parse(resText) : {};
    } catch (parseError) {
      return NextResponse.json(
        { status: backendRes.status, error: 'Backend Error', errors: { global: resText || 'Lỗi định dạng phản hồi hệ thống' } },
        { status: backendRes.status }
      );
    }

    // 3. Nếu Backend trả lỗi nghiệp vụ, forward nguyên vẹn cấu trúc {status, error, errors} sang VS Code
    if (!backendRes.ok) {
      return NextResponse.json(resData, { status: backendRes.status });
    }

    // 4. Đăng nhập thành công -> Trích xuất token và găm vào httpOnly Cookie
    const accessToken = resData.data?.accessToken || resData.accessToken;
    if (!accessToken) {
      return NextResponse.json(
        { status: 500, error: 'BFF Error', errors: { global: 'Cấu trúc Token không hợp lệ từ Backend' } },
        { status: 500 }
      );
    }

    const cookieStore = await cookies();
    cookieStore.set('authToken', accessToken, {
      httpOnly: true,
      secure: process.env.NODE_ENV === 'production',
      sameSite: 'lax',
      path: '/',
      maxAge: 60 * 60 * 24, // 1 ngày
    });

    return NextResponse.json({
      status: 200,
      message: 'Đăng nhập thành công qua BFF Gateway!',
    });
  } catch (error: any) {
    return NextResponse.json(
      { status: 500, error: 'Internal Server Error', errors: { global: error.message || 'Lỗi kết nối BFF Gateway' } },
      { status: 500 }
    );
  }
}