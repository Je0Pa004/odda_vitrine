import { Injectable } from '@angular/core';
import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DemandeStageRequest, DemandeStageResponse } from '../models/demande-stage.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class DemandeStageService {
  private base = `${environment.apiBase}/hr`;

  constructor(private http: HttpClient) { }

  // applicationRequest: DemandeStageRequest, cv: File, lettre?: File
  apply(applicationRequest: DemandeStageRequest, cv: File, lettre?: File): Observable<DemandeStageResponse> {
    const form = new FormData();
    // server expects a part named "application" containing JSON
    form.append('application', new Blob([JSON.stringify(applicationRequest)], { type: 'application/json' }));
    form.append('cv', cv, cv.name);
    if (lettre) {
      form.append('lettreRecommandation', lettre, lettre.name);
    }
    return this.http.post<DemandeStageResponse>(`${this.base}/applications`, form);
  }

  getByTrackingId(trackingId: string) {
    return this.http.get<DemandeStageResponse>(`${this.base}/applications/${trackingId}`);
  }

  getAll(): Observable<DemandeStageResponse[]> {
    return this.http.get<DemandeStageResponse[]>(`${this.base}/applications`);
  }

  delete(trackingId: string): Observable<void> {
    return this.http.delete<void>(`${this.base}/applications/${trackingId}`);
  }

  updateStatus(trackingId: string, status: string): Observable<DemandeStageResponse> {
    return this.http.put<DemandeStageResponse>(`${this.base}/applications/${trackingId}/status?status=${status}`, {});
  }
}
