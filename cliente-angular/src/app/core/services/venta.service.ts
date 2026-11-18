import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../config/api.config';
import { CrearVentaRequest, Venta } from '../models/venta.model';

@Injectable({ providedIn: 'root' })
export class VentaService {
  private readonly http = inject(HttpClient);
  private readonly url = `${inject(API_BASE_URL)}/ventas`;

  listar(): Observable<Venta[]> {
    return this.http.get<Venta[]>(this.url);
  }

  obtener(id: string): Observable<Venta> {
    return this.http.get<Venta>(`${this.url}/${encodeURIComponent(id)}`);
  }

  registrar(solicitud: CrearVentaRequest): Observable<Venta> {
    return this.http.post<Venta>(this.url, solicitud);
  }
}
