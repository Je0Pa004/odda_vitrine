import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Annonce } from '../models/annonce.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AnnonceService {
  private base = `${environment.apiBase}/hr/annonces`;

  constructor(private http: HttpClient) { }

  getAll(): Observable<Annonce[]> {
    return this.http.get<Annonce[]>(this.base);
  }

  getByTrackingId(trackingId: string) {
    return this.http.get<Annonce>(`${this.base}/${trackingId}`);
  }

  create(formData: FormData) {
    return this.http.post<Annonce>(this.base, formData);
  }

  update(trackingId: string, formData: FormData) {
    return this.http.put<Annonce>(`${this.base}/${trackingId}`, formData);
  }

  delete(trackingId: string) {
    return this.http.delete<void>(`${this.base}/${trackingId}`);
  }
}
