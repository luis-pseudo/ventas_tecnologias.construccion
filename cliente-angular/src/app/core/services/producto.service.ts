import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../config/api.config';
import { CrearProductoRequest, Producto } from '../models/producto.model';

@Injectable({ providedIn: 'root' })
export class ProductoService {
  private readonly http = inject(HttpClient);
  private readonly url = `${inject(API_BASE_URL)}/productos`;

  listar(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.url);
  }

  obtener(id: string): Observable<Producto> {
    return this.http.get<Producto>(`${this.url}/${encodeURIComponent(id)}`);
  }

  crear(solicitud: CrearProductoRequest): Observable<Producto> {
    return this.http.post<Producto>(this.url, solicitud);
  }
}
