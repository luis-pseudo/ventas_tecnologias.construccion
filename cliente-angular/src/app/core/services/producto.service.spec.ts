import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { apiErrorInterceptor } from '../interceptors/api-error.interceptor';
import { ApiError } from '../models/api-error.model';
import { ProductoService } from './producto.service';

describe('ProductoService', () => {
  let servicio: ProductoService;
  let http: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(withInterceptors([apiErrorInterceptor])),
        provideHttpClientTesting(),
      ],
    });
    servicio = TestBed.inject(ProductoService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => http.verify());

  it('lista productos con GET /api/v1/productos', () => {
    let recibido: unknown;
    servicio.listar().subscribe((r) => (recibido = r));

    const req = http.expectOne('/api/v1/productos');
    expect(req.request.method).toBe('GET');
    req.flush([{ id: 'a1', nombre: 'Teclado', precio: 450 }]);

    expect(recibido).toEqual([{ id: 'a1', nombre: 'Teclado', precio: 450 }]);
  });

  it('crea un producto con POST y el cuerpo tipado', () => {
    servicio.crear({ nombre: 'Mouse', precio: 199.9 }).subscribe();

    const req = http.expectOne('/api/v1/productos');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ nombre: 'Mouse', precio: 199.9 });
    req.flush({ id: 'b2', nombre: 'Mouse', precio: 199.9 }, { status: 201, statusText: 'Created' });
  });

  it('codifica el id al obtener un producto', () => {
    servicio.obtener('a/b').subscribe();

    http.expectOne('/api/v1/productos/a%2Fb').flush({ id: 'a/b', nombre: 'X', precio: 1 });
  });

  it('error 400 de la API llega normalizado como ApiError (caso negativo)', () => {
    let error: ApiError | undefined;
    servicio.crear({ nombre: '', precio: -1 }).subscribe({ error: (e: ApiError) => (error = e) });

    http.expectOne('/api/v1/productos').flush(
      {
        status: 400,
        code: 'VALIDATION_ERROR',
        message: 'La solicitud contiene datos inválidos',
        details: [{ field: 'nombre', message: 'es obligatorio' }],
      },
      { status: 400, statusText: 'Bad Request' },
    );

    expect(error?.code).toBe('VALIDATION_ERROR');
    expect(error?.details?.[0].field).toBe('nombre');
  });

  it('API caída llega como NETWORK_ERROR (caso límite)', () => {
    let error: ApiError | undefined;
    servicio.listar().subscribe({ error: (e: ApiError) => (error = e) });

    http.expectOne('/api/v1/productos').error(new ProgressEvent('error'), { status: 0 });

    expect(error?.code).toBe('NETWORK_ERROR');
  });
});
