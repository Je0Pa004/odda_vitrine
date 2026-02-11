export interface User {
  trackingId?: string;
  email: string;
  roles?: string[];
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  trackingId?: string;
  email?: string;
  message?: string;
}
