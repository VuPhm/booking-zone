import { apiClient } from '@/lib/apiClient';
import { Booking } from '@/types';

export const bookingService = {
  async createBooking(payload: { bookingDate: string; serviceId: number; timeSlotId: number; userId: number }): Promise<Booking> {
    const { data } = await apiClient.post<Booking>('/bookings', {
      bookingDate: payload.bookingDate,
      serviceId: payload.serviceId,
      timeSlotId: payload.timeSlotId
    }, {
      headers: { 'X-User-Id': payload.userId } // Truyền tạm thời ID user qua Header để test nhanh
    });
    return data;
  },
  async getMyHistory(userId: number): Promise<Booking[]> {
    const { data } = await apiClient.get<Booking[]>('/bookings/my-history', {
      headers: { 'X-User-Id': userId }
    });
    return data;
  }
};