import { TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { of } from 'rxjs';
import { CarritoService } from '../../core/services/carrito.service';
import { VentaService } from '../../core/services/venta.service';
import { Carrito } from './carrito';

describe('Carrito vista', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Carrito],
      providers: [
        provideRouter([]),
        {
          provide: VentaService,
          useValue: {
            registrar: () => of({ id: 'v1', lineas: [], subtotal: 0, total: 0 }),
          },
        },
      ],
    }).compileComponents();
  });

  it('muestra estado vacío', () => {
    const fixture = TestBed.createComponent(Carrito);
    fixture.detectChanges();
    expect((fixture.nativeElement as HTMLElement).textContent).toContain('El carrito está vacío');
  });

  it('renderiza líneas cuando hay productos', () => {
    const fixture = TestBed.createComponent(Carrito);
    const carrito = TestBed.inject(CarritoService);
    carrito.agregar({ id: 'p1', nombre: 'Teclado', precio: 450 }, 2);

    fixture.detectChanges();

    expect((fixture.nativeElement as HTMLElement).textContent).toContain('Teclado');
    expect((fixture.nativeElement as HTMLElement).textContent).toContain('Total');
  });
});
