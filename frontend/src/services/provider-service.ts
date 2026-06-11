import { apiClient } from '@/lib/apiClient';
import {
  Service,
  CreateServiceRequest,
} from '@/types/service';

const BASE_URL = '/api/v1/provider/services';

export const providerServiceApi = {
  async getServices(): Promise<Service[]> {
    const response = await apiClient.get<Service[]>(BASE_URL);
    return response.data;
  },

  async createService(
    payload: CreateServiceRequest
  ): Promise<Service> {
    const response = await apiClient.post<Service>(
      BASE_URL,
      payload
    );

    return response.data;
  },

  async activateService(id: number): Promise<void> {
    await apiClient.patch(
      `${BASE_URL}/${id}/activate`
    );
  },

  async deactivateService(id: number): Promise<void> {
    await apiClient.patch(
      `${BASE_URL}/${id}/deactivate`
    );
  },
};