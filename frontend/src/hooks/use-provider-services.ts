import { useEffect, useState } from 'react';
import { providerServiceApi } from '@/services/provider-service';
import { Service } from '@/types/service';

export function useProviderServices() {
  const [services, setServices] = useState<Service[]>([]);
  const [loading, setLoading] = useState(true);

  async function loadServices() {
    try {
      setLoading(true);

      const data =
        await providerServiceApi.getServices();

      setServices(data);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadServices();
  }, []);

  return {
    services,
    loading,
    reload: loadServices,
  };
}