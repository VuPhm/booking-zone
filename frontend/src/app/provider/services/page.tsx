'use client';

console.log('SERVICE PAGE LOADED');
import { useState } from 'react';

import { Button } from '@/components/ui/button';

import { ServiceCard } from '@/components/provider/service-card';

import { useProviderServices } from '@/hooks/use-provider-services';

import { providerServiceApi } from '@/services/provider-service';

import { Service } from '@/types/service';

export default function ProviderServicesPage() {
  const {
    services,
    loading,
    reload,
  } = useProviderServices();

  const [updatingId, setUpdatingId] =
    useState<number | null>(null);

  async function handleToggleStatus(
    service: Service
  ) {
    try {
      setUpdatingId(service.id);

      if (service.isActive) {
        await providerServiceApi.deactivateService(
          service.id
        );
      } else {
        await providerServiceApi.activateService(
          service.id
        );
      }

      await reload();
    } catch (error) {
      console.error(error);
    } finally {
      setUpdatingId(null);
    }
  }

  return (
    <div className="container mx-auto py-6 space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-bold">
          Quản lý dịch vụ
        </h1>

        <Button>
          Thêm dịch vụ
        </Button>
      </div>

      {loading ? (
        <p>Đang tải dữ liệu...</p>
      ) : services.length === 0 ? (
        <div className="rounded-lg border p-8 text-center">
          Chưa có dịch vụ nào
        </div>
      ) : (
        <div className="grid gap-4 md:grid-cols-2 xl:grid-cols-3">
          {services.map((service) => (
            <ServiceCard
              key={service.id}
              service={service}
              loading={
                updatingId === service.id
              }
              onToggleStatus={
                handleToggleStatus
              }
            />
          ))}
        </div>
      )}
    </div>
  );
}