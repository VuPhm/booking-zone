import { NextResponse } from 'next/server';

export async function POST(request: Request) {
  try {
    const body = await request.json();

    const backendRes = await fetch('http://localhost:8080/api/v1/auth/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    });

    const resData = await backendRes.json();

    if (!backendRes.ok) {
      return NextResponse.json(resData, { status: backendRes.status });
    }

    return NextResponse.json({ status: 201, message: 'Đăng ký thành công!' }, { status: 201 });
  } catch (error) {
    return NextResponse.json(
      { status: 500, error: 'Internal Server Error', errors: { global: 'Lỗi kết nối BFF Gateway' } },
      { status: 500 }
    );
  }
}