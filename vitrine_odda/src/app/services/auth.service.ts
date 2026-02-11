import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { LoginRequest, LoginResponse, User } from '../models/user.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private base = `${environment.apiBase}/auth`;
  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<LoginResponse> {
    const body: LoginRequest = { email, password };
    return this.http.post<LoginResponse>(`${this.base}/login`, body);
  }

  // Optional helper to map login response to a User model
  mapToUser(resp: LoginResponse | null): User | null {
    if (!resp || !resp.email) return null;
    return { trackingId: resp.trackingId, email: resp.email };
  }
}
