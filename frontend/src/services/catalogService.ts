import { apiClient } from '@/lib/apiClient';
import { ServiceCategory, ServiceItem } from '@/types';

export const catalogService = {
  // Lấy toàn bộ danh mục ngành để làm bộ lọc
  async getCategories(): Promise<ServiceCategory[]> {
    const { data } = await apiClient.get<ServiceCategory[]>('/categories');
    return data;
  },

  // Lấy danh sách dịch vụ (hỗ trợ lọc theo categoryId trực tiếp bằng RequestParam)
  async getServices(categoryId?: number): Promise<ServiceItem[]> {
    const url = categoryId ? `/services?categoryId=${categoryId}` : '/services';
    const { data } = await apiClient.get<ServiceItem[]>(url);
    return data;
  }
};