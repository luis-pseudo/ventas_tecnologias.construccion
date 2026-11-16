import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { apiErrorInterceptor } from '../interceptors/api-error.interceptor';
import { ApiError } from '../models/api-error.model';
import { Venta } from '../models/venta.model';
import { VentaService } from './venta.service';

const venta: Venta = {
  id: 'v1',
  lineas: [{ producto: 'Teclado', cantidad: 2, precioUnitario: 450, subtotal: 900 }],
  subtotal: 900,
  total: 900,
};

describe('VentaService', () => {
  let servicio: VentaService;
  let http: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(withInterceptors([apiErrorInterceptor])),
        provideHttpClientTesting(),
      ],
    });
    servicio = TestBed.inject(VentaService);
    http = TestBed.inject(HttpTestingController);
  });

  afterEach(() => http.verify());

  it('lista las ventas con GET /api/v1/ventas', () => {
    let recibido: Venta[] = [];
    servicio.listar().subscribe((r) => (recibido = r));

    const req = http.expectOne('/api/v1/ventas');
    expect(req.request.method).toBe('GET');
    req.flush([venta]);

    expect(recibido[0].total).toBe(900);
  });

  it('registra una venta con POST y las líneas del carrito', () => {
    let recibida: Venta | undefined;
    servicio
      .registrar({ lineas: [{ productoId: 'p1', cantidad: 2 }] })
      .subscribe((v) => (recibida = v));

    const req = http.expectOne('/api/v1/ventas');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ lineas: [{ productoId: 'p1', cantidad: 2 }] });
    req.flush(venta, { status: 201, statusText: 'Created' });

    expect(recibida?.id).toBe('v1');
  });

  it('producto inexistente (404 NOT_FOUND) se propaga como ApiError (caso negativo)', () => {
    let error: ApiError | undefined;
    servicio
      .registrar({ lineas: [{ productoId: 'no-existe', cantidad: 1 }] })
      .subscribe({ error: (e: ApiError) => (error = e) });

    http.expectOne('/api/v1/ventas').flush(
      { status: 404, code: 'NOT_FOUND', message: 'Producto no encontrado: no-existe' },
      { status: 404, statusText: 'Not Found' },
    );

    expect(error?.status).toBe(404);
    expect(error?.code).toBe('NOT_FOUND');
  });
});
