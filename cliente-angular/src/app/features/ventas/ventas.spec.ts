import { TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { VentaService } from '../../core/services/venta.service';
import { Ventas } from './ventas';

describe('Ventas vista', () => {
  it('muestra vacío cuando no hay ventas', async () => {
    await TestBed.configureTestingModule({
      imports: [Ventas],
      providers: [
        {
          provide: VentaService,
          useValue: { listar: () => of([]) },
        },
      ],
    }).compileComponents();

    const fixture = TestBed.createComponent(Ventas);
    fixture.detectChanges();
    await fixture.whenStable();
    fixture.detectChanges();

    expect((fixture.nativeElement as HTMLElement).textContent).toContain('Aún no hay ventas');
  });
});