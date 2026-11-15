import { TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { App } from './app';

describe('App', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [App],
      providers: [provideRouter([])],
    }).compileComponents();
  });

  it('muestra la navegación principal', async () => {
    const fixture = TestBed.createComponent(App);
    await fixture.whenStable();

    const enlaces = Array.from(fixture.nativeElement.querySelectorAll('nav a')).map((a) =>
      (a as HTMLElement).textContent?.trim(),
    );
    expect(enlaces).toEqual(['Catálogo', 'Carrito', 'Ventas']);
  });
});
