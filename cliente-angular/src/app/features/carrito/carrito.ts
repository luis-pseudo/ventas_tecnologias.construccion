import { CurrencyPipe } from '@angular/common';
import { Component, computed, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { mensajeDeError } from '../../core/models/api-error.model';
import { CarritoService } from '../../core/services/carrito.service';
import { VentaService } from '../../core/services/venta.service';

@Component({
  selector: 'app-carrito',
  imports: [CurrencyPipe, RouterLink],
  templateUrl: './carrito.html',
})
export class Carrito {
  private readonly carrito = inject(CarritoService);
  private readonly ventasApi = inject(VentaService);

  protected readonly lineas = this.carrito.lineas;
  protected readonly unidades = this.carrito.unidades;
  protected readonly total = this.carrito.total;

  protected readonly registrando = signal(false);
  protected readonly exito = signal('');
  protected readonly error = signal('');

  protected readonly puedeRegistrar = computed(
    () => this.lineas().length > 0 && !this.registrando(),
  );

  protected cambiarCantidad(productoId: string, valor: string): void {
    const cantidad = Number(valor);
    const entero = Number.isFinite(cantidad) ? Math.trunc(cantidad) : 0;
    this.carrito.cambiarCantidad(productoId, entero);
  }

  protected quitar(productoId: string): void {
    this.carrito.quitar(productoId);
  }

  protected vaciar(): void {
    this.carrito.vaciar();
  }

  protected registrarVenta(): void {
    if (this.lineas().length === 0) {
      return;
    }
    this.registrando.set(true);
    this.error.set('');
    this.exito.set('');

    this.ventasApi.registrar(this.carrito.aSolicitudVenta()).subscribe({
      next: (venta) => {
        this.carrito.vaciar();
        this.exito.set('Venta registrada con id ' + venta.id);
        this.registrando.set(false);
      },
      error: (e: unknown) => {
        this.error.set(mensajeDeError(e));
        this.registrando.set(false);
      },
    });
  }
}
