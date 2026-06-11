'use client';

import { Service } from '@/types/service';
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';

interface Props {
  service: Service;
  onToggleStatus: (service: Service) => void;
  loading?: boolean;
}

export function ServiceCard({
  service,
  onToggleStatus,
  loading,
}: Props) {
  return (
    <Card>
      <CardHeader>
        <CardTitle>{service.name}</CardTitle>
      </CardHeader>

      <CardContent className="space-y-3">
        <p className="text-sm text-muted-foreground">
          {service.description}
        </p>

        <div className="text-sm">
          <span className="font-medium">
            {service.price.toLocaleString('vi-VN')}đ
          </span>
          {' • '}
          <span>{service.duration} phút</span>
        </div>

        <Badge
          variant={
            service.isActive
              ? 'default'
              : 'secondary'
          }
        >
          {service.isActive
            ? 'Đang hoạt động'
            : 'Ngừng hoạt động'}
        </Badge>
      </CardContent>

      <CardFooter>
        <Button
          variant={
            service.isActive
              ? 'destructive'
              : 'default'
          }
          onClick={() => onToggleStatus(service)}
          disabled={loading}
        >
          {service.isActive
            ? 'Ngưng hoạt động'
            : 'Kích hoạt'}
        </Button>
      </CardFooter>
    </Card>
  );
}