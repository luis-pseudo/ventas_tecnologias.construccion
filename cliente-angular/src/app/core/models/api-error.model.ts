import { HttpErrorResponse } from '@angular/common/http';

export interface ApiErrorDetalle {
  field: string;
  message: string;
}

/** Error normalizado: mismo formato que ErrorResponse de la API, más NETWORK_ERROR y UNKNOWN. */
export interface ApiError {
  status: number;
  code: string;
  message: string;
  details?: ApiErrorDetalle[];
}

const ESTADOS_SIN_API = [0, 502, 503, 504];

export function esApiError(valor: unknown): valor is ApiError {
  return (
    typeof valor === 'object' &&
    valor !== null &&
    'code' in valor &&
    'message' in valor &&
    'status' in valor
  );
}

/** Convierte un HttpErrorResponse en ApiError sin exponer detalles internos. */
export function aApiError(error: HttpErrorResponse): ApiError {
  const cuerpo = error.error as Partial<ApiError> | null;

  if (cuerpo && typeof cuerpo === 'object' && typeof cuerpo.code === 'string') {
    return {
      status: error.status,
      code: cuerpo.code,
      message: cuerpo.message ?? 'La API devolvió un error',
      details: cuerpo.details,
    };
  }
  if (ESTADOS_SIN_API.includes(error.status)) {
    return {
      status: error.status,
      code: 'NETWORK_ERROR',
      message: 'No se pudo conectar con la API. Verifica que esté en ejecución en el puerto 8080.',
    };
  }
  return {
    status: error.status,
    code: 'UNKNOWN',
    message: `Error inesperado (HTTP ${error.status})`,
  };
}

/** Texto listo para mostrar en pantalla (mensaje + detalle por campo). */
export function mensajeDeError(error: unknown): string {
  if (!esApiError(error)) {
    return 'Ocurrió un error inesperado';
  }
  if (error.details && error.details.length > 0) {
    const detalle = error.details.map((d) => `${d.field}: ${d.message}`).join('; ');
    return `${error.message} (${detalle})`;
  }
  return error.message;
}
