import { InjectionToken } from '@angular/core';

/**
 * URL base de la API REST (Spring Boot, puerto 8080).
 * En desarrollo es relativa: `ng serve` la reenvía a http://localhost:8080
 * mediante proxy.conf.json, por lo que el navegador ve un solo origen
 * y no se necesita configurar CORS en el backend.
 */
export const API_BASE_URL = new InjectionToken<string>('API_BASE_URL', {
  providedIn: 'root',
  factory: () => '/api/v1',
});
