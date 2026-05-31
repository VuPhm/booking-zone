import { NextResponse } from 'next/server';
import { cookies } from 'next/headers';

export async function POST() {
  const cookieStore = await cookies();
  
  // Xóa bỏ cookie chứa JWT bám trên trình duyệt
  cookieStore.delete('authToken');

  return NextResponse.json({
    status: 200,
    message: 'Đã xóa phiên làm việc an toàn!',
  });
}