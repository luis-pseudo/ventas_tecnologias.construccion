import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { apiErrorInterceptor } from '../../core/interceptors/api-error.interceptor';
import { CarritoService } from '../../core/services/carrito.service';
import { Catalogo } from './catalogo';

const URL = '/api/v1/productos';

describe('Catalogo', () => {
  let fixture: ComponentFixture<Catalogo>;
  let http: HttpTestingController;
  let html: HTMLElement;

  const refrescar = async () => {
    await fixture.whenStable();
    fixture.detectChanges();
  };
  const escribir = (id: string, valor: string) => {
    const entrada = html.querySelector<HTMLInputElement>(`#${id}`)!;
    entrada.value = valor;
    entrada.dispatchEvent(new Event('input'));
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Catalogo],
      providers: [
        provideHttpClient(withInterceptors([apiErrorInterceptor])),
        provideHttpClientTesting(),
      ],
    }).compileComponents();
    fixture = TestBed.createComponent(Catalogo);
    http = TestBed.inject(HttpTestingController);
    html = fixture.nativeElement as HTMLElement;
  });

  afterEach(() => http.verify());

  it('muestra el estado de carga y luego la lista de productos', async () => {
    fixture.detectChanges();
    expect(html.textContent).toContain('Cargando productos');

    http.expectOne(URL).flush([{ id: 'p1', nombre: 'Teclado', precio: 450 }]);
    await refrescar();

    expect(html.textContent).not.toContain('Cargando productos');
    expect(html.querySelectorAll('ul.lista li')).toHaveLength(1);
    expect(html.textContent).toContain('Teclado');
    expect(html.textContent).toContain('$450.00');
  });

  it('muestra el estado vacío cuando no hay productos', async () => {
    fixture.detectChanges();
    http.expectOne(URL).flush([]);
    await refrescar();

    expect(html.textContent).toContain('Todavía no hay productos');
  });

  it('muestra el error de la API y permite reintentar', async () => {
    fixture.detectChanges();
    http.expectOne(URL).error(new ProgressEvent('error'), { status: 0 });
    await refrescar();

    expect(html.querySelector('[role="alert"]')?.textContent).toContain('No se pudo conectar');

    html.querySelector<HTMLButtonElement>('button.secundario')!.click();
    http.expectOne(URL).flush([{ id: 'p1', nombre: 'Teclado', precio: 450 }]);
    await refrescar();

    expect(html.querySelectorAll('ul.lista li')).toHaveLength(1);
  });

  it('no envía el formulario inválido y muestra los errores de campo (caso negativo)', async () => {
    fixture.detectChanges();
    http.expectOne(URL).flush([]);
    await refrescar();

    html.querySelector<HTMLFormElement>('form')!.dispatchEvent(new Event('submit'));
    await refrescar();

    http.expectNone(URL); // no hubo POST
    expect(html.textContent).toContain('Escribe el nombre del producto.');
    expect(html.textContent).toContain('Escribe el precio.');
  });

  it('registra un producto válido, lo agrega a la lista y limpia el formulario', async () => {
    fixture.detectChanges();
    http.expectOne(URL).flush([]);
    await refrescar();

    escribir('nombre', '  Monitor ');
    escribir('precio', '3200');
    html.querySelector<HTMLFormElement>('form')!.dispatchEvent(new Event('submit'));

    const post = http.expectOne(URL);
    expect(post.request.method).toBe('POST');
    expect(post.request.body).toEqual({ nombre: 'Monitor', precio: 3200 });
    post.flush({ id: 'p9', nombre: 'Monitor', precio: 3200 }, { status: 201, statusText: 'Created' });
    await refrescar();

    expect(html.querySelector('[role="status"]')?.textContent).toContain('Producto «Monitor» registrado');
    expect(html.querySelectorAll('ul.lista li')).toHaveLength(1);
    expect(html.querySelector<HTMLInputElement>('#nombre')!.value).toBe('');
  });

  it('muestra el error 400 de la API al registrar (caso negativo)', async () => {
    fixture.detectChanges();
    http.expectOne(URL).flush([]);
    await refrescar();

    escribir('nombre', 'Monitor');
    escribir('precio', '10');
    html.querySelector<HTMLFormElement>('form')!.dispatchEvent(new Event('submit'));
    http.expectOne(URL).flush(
      {
        status: 400,
        code: 'VALIDATION_ERROR',
        message: 'La solicitud contiene datos inválidos',
        details: [{ field: 'precio', message: 'debe ser mayor que 0' }],
      },
      { status: 400, statusText: 'Bad Request' },
    );
    await refrescar();

    expect(html.querySelector('[role="alert"]')?.textContent).toContain('precio: debe ser mayor que 0');
    expect(html.querySelectorAll('ul.lista li')).toHaveLength(0);
  });

  it('agrega un producto al carrito', async () => {
    fixture.detectChanges();
    http.expectOne(URL).flush([{ id: 'p1', nombre: 'Teclado', precio: 450 }]);
    await refrescar();

    html.querySelector<HTMLButtonElement>('ul.lista button')!.click();
    await refrescar();

    const carrito = TestBed.inject(CarritoService);
    expect(carrito.unidades()).toBe(1);
    expect(html.textContent).toContain('«Teclado» agregado al carrito');
  });
});
