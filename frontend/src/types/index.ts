export type UserRole = 'ADMIN' | 'CUSTOMER' | 'STAFF';

export interface User {
  id: number;
  username: string;
  fullName: string;
  role: UserRole;
}

export interface ServiceCategory {
  id: number;
  name: string;
  description?: string;
}

export interface ServiceItem {
  id: number;
  name: string;
  description?: string;
  price: number;
  duration: number;
  category: ServiceCategory;
}

export interface TimeSlot {
  id: number;
  startTime: string; // "08:00:00"
  endTime: string;
  status: 'AVAILABLE' | 'BOOKED';
}