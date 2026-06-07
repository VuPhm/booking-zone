export interface Service {
  id: number;
  name: string;
  description: string;
  price: number;
  duration: number;
  imageUrl: string;
  isActive: boolean;
}

export interface CreateServiceRequest {
  name: string;
  description: string;
  price: number;
  duration: number;
  imageUrl: string;
}