import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';

export function middleware(request: NextRequest) {
  const token = request.cookies.get('authToken')?.value;
  const { pathname } = request.nextUrl;

  // Cấu hình các tuyến đường cần bảo vệ nghiêm ngặt
  const isProtectedRoute = pathname.startsWith('/admin') || pathname.startsWith('/provider');

  if (isProtectedRoute && !token) {
    // Nếu không có Token, redirect ngay lập tức về trang login từ tầng Server
    return NextResponse.redirect(new URL('/login', request.url));
  }

  return NextResponse.next();
}

export const config = {
  matcher: ['/admin/:path*', '/provider/:path*'],
};