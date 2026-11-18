import { Injectable, computed, signal } from '@angular/core';
import { Producto } from '../models/producto.model';
import { CrearVentaRequest } from '../models/venta.model';

export interface LineaCarrito {
  producto: Producto;
  cantidad: number;
}

const aDinero = (valor: number): number => Math.round(valor * 100) / 100;

/** Estado del carrito en memoria (signals). Se vacía al registrar la venta. */
@Injectable({ providedIn: 'root' })
export class CarritoService {
  private readonly _lineas = signal<LineaCarrito[]>([]);

  readonly lineas = this._lineas.asReadonly();
  readonly unidades = computed(() => this._lineas().reduce((suma, l) => suma + l.cantidad, 0));
  readonly total = computed(() =>
    aDinero(this._lineas().reduce((suma, l) => suma + l.producto.precio * l.cantidad, 0)),
  );

  agregar(producto: Producto, cantidad = 1): void {
    if (!Number.isInteger(cantidad) || cantidad < 1) {
      throw new Error('La cantidad debe ser un entero mayor o igual a 1');
    }
    this._lineas.update((lineas) => {
      const existe = lineas.some((l) => l.producto.id === producto.id);
      return existe
        ? lineas.map((l) =>
            l.producto.id === producto.id ? { ...l, cantidad: l.cantidad + cantidad } : l,
          )
        : [...lineas, { producto, cantidad }];
    });
  }

  /** Una cantidad menor a 1 quita la línea. */
  cambiarCantidad(productoId: string, cantidad: number): void {
    if (!Number.isInteger(cantidad) || cantidad < 1) {
      this.quitar(productoId);
      return;
    }
    this._lineas.update((lineas) =>
      lineas.map((l) => (l.producto.id === productoId ? { ...l, cantidad } : l)),
    );
  }

  quitar(productoId: string): void {
    this._lineas.update((lineas) => lineas.filter((l) => l.producto.id !== productoId));
  }

  vaciar(): void {
    this._lineas.set([]);
  }

  aSolicitudVenta(): CrearVentaRequest {
    return {
      lineas: this._lineas().map((l) => ({ productoId: l.producto.id, cantidad: l.cantidad })),
    };
  }
}
