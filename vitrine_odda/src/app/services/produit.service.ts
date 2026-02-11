import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produit } from '../models/produit.model';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ProduitService {
  private base = `${environment.apiBase}/produits`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Produit[]> {
    return this.http.get<Produit[]>(this.base);
  }

  getByTrackingId(trackingId: string) {
    return this.http.get<Produit>(`${this.base}/${trackingId}`);
  }

  getByCategory(categorie: string) {
    return this.http.get<Produit[]>(`${this.base}/categorie/${categorie}`);
  }

  create(product: Produit) {
    return this.http.post<Produit>(this.base, product);
  }

  delete(trackingId: string) {
    return this.http.delete<void>(`${this.base}/${trackingId}`);
  }
}
