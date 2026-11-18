import { HttpErrorResponse } from '@angular/common/http';
import { aApiError, mensajeDeError } from './api-error.model';

describe('api-error.model', () => {
  it('conserva code, message y details del formato de la API', () => {
    const error = new HttpErrorResponse({
      status: 400,
      error: {
        status: 400,
        code: 'VALIDATION_ERROR',
        message: 'La solicitud contiene datos inválidos',
        details: [{ field: 'precio', message: 'debe ser mayor que 0' }],
      },
    });

    const apiError = aApiError(error);

    expect(apiError.code).toBe('VALIDATION_ERROR');
    expect(apiError.status).toBe(400);
    expect(mensajeDeError(apiError)).toBe(
      'La solicitud contiene datos inválidos (precio: debe ser mayor que 0)',
    );
  });

  it('marca NETWORK_ERROR cuando la API no responde (status 0 o 504)', () => {
    expect(aApiError(new HttpErrorResponse({ status: 0 })).code).toBe('NETWORK_ERROR');
    expect(aApiError(new HttpErrorResponse({ status: 504 })).code).toBe('NETWORK_ERROR');
  });

  it('usa UNKNOWN para errores sin el formato esperado, sin filtrar el cuerpo', () => {
    const apiError = aApiError(new HttpErrorResponse({ status: 500, error: '<html>stacktrace</html>' }));

    expect(apiError.code).toBe('UNKNOWN');
    expect(apiError.message).toBe('Error inesperado (HTTP 500)');
  });

  it('mensajeDeError devuelve un texto genérico si no recibe un ApiError', () => {
    expect(mensajeDeError(new Error('x'))).toBe('Ocurrió un error inesperado');
  });
});
