import { NextResponse } from 'next/server';
import type { NextRequest } from 'next/server';

// Định nghĩa danh sách các trang Auth (Chỉ cho khách vào, đã login thì cấm)
const authRoutes = ['/login', '/register'];

// Định nghĩa danh sách các trang Quản trị (Bắt buộc phải là ADMIN mới vào được)
const adminRoutes = ['/admin'];

export function middleware(request: NextRequest) {
  const { nextUrl, cookies } = request;

  // Bóc tách token và role trực tiếp từ Cookie hệ thống
  const token = cookies.get('auth-token')?.value;
  const role = cookies.get('auth-role')?.value;

  const isAuthRoute = authRoutes.some((route) => nextUrl.pathname.startsWith(route));
  const isAdminRoute = adminRoutes.some((route) => nextUrl.pathname.startsWith(route));

  // TÌNH HUỐNG 1: Đã đăng nhập nhưng cố tình truy cập vào trang /login hoặc /register
  if (token && isAuthRoute) {
    if (role === 'ADMIN') {
      return NextResponse.redirect(new URL('/admin', request.url));
    }
    return NextResponse.redirect(new URL('/', request.url));
  }

  // TÌNH HUỐNG 2: Chưa đăng nhập nhưng cố tình truy cập vào trang quản trị /admin
  if (!token && isAdminRoute) {
    return NextResponse.redirect(new URL('/login', request.url));
  }

  // TÌNH HUỐNG 3: Đã đăng nhập nhưng tài khoản CUSTOMER cố tình vào trang /admin
  if (token && role !== 'ADMIN' && isAdminRoute) {
    return NextResponse.redirect(new URL('/', request.url));
  }

  // Cho đi tiếp bình thường nếu không vi phạm điều kiện nào
  return NextResponse.next(); // Đã sửa: Chuyển từ NextResponse.get() sang NextResponse.next()
}

// Cấu hình màng lọc chỉ chạy Middleware qua các cụm trang này để tối ưu hiệu năng
export const config = {
  matcher: ['/login', '/register', '/admin/:path*'],
};