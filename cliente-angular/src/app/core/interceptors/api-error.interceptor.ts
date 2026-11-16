import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';
import { aApiError } from '../models/api-error.model';

/** Normaliza cualquier error HTTP a ApiError para que la UI maneje un solo formato. */
export const apiErrorInterceptor: HttpInterceptorFn = (req, next) =>
  next(req).pipe(
    catchError((error: unknown) =>
      throwError(() => (error instanceof HttpErrorResponse ? aApiError(error) : error)),
    ),
  );
