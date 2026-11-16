import { CurrencyPipe } from '@angular/common';
import { Component, OnInit, inject, signal } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { mensajeDeError } from '../../core/models/api-error.model';
import { Producto } from '../../core/models/producto.model';
import { CarritoService } from '../../core/services/carrito.service';
import { ProductoService } from '../../core/services/producto.service';

type EstadoCarga = 'cargando' | 'listo' | 'error';

@Component({
  selector: 'app-catalogo',
  imports: [ReactiveFormsModule, CurrencyPipe],
  templateUrl: './catalogo.html',
})
export class Catalogo implements OnInit {
  private readonly productosApi = inject(ProductoService);
  private readonly carrito = inject(CarritoService);

  protected readonly productos = signal<Producto[]>([]);
  protected readonly estado = signal<EstadoCarga>('cargando');
  protected readonly errorCarga = signal('');

  protected readonly guardando = signal(false);
  protected readonly exito = signal('');
  protected readonly errorAlta = signal('');
  protected readonly agregado = signal('');

  protected readonly formulario = new FormGroup({
    nombre: new FormControl('', {
      nonNullable: true,
      validators: [Validators.required, Validators.maxLength(100)],
    }),
    precio: new FormControl<number | null>(null, [Validators.required, Validators.min(0.01)]),
  });

  ngOnInit(): void {
    this.cargar();
  }

  protected cargar(): void {
    this.estado.set('cargando');
    this.errorCarga.set('');
    this.productosApi.listar().subscribe({
      next: (productos) => {
        this.productos.set(productos);
        this.estado.set('listo');
      },
      error: (error: unknown) => {
        this.errorCarga.set(mensajeDeError(error));
        this.estado.set('error');
      },
    });
  }

  protected guardar(): void {
    if (this.formulario.invalid) {
      this.formulario.markAllAsTouched();
      return;
    }
    const { nombre, precio } = this.formulario.getRawValue();
    this.guardando.set(true);
    this.exito.set('');
    this.errorAlta.set('');
    this.productosApi.crear({ nombre: nombre.trim(), precio: precio as number }).subscribe({
      next: (producto) => {
        this.productos.update((lista) => [...lista, producto]);
        this.exito.set(`Producto «${producto.nombre}» registrado`);
        this.formulario.reset();
        this.guardando.set(false);
      },
      error: (error: unknown) => {
        this.errorAlta.set(mensajeDeError(error));
        this.guardando.set(false);
      },
    });
  }

  protected agregarAlCarrito(producto: Producto): void {
    this.carrito.agregar(producto);
    this.agregado.set(`«${producto.nombre}» agregado al carrito`);
  }

  protected invalido(campo: 'nombre' | 'precio'): boolean {
    const control = this.formulario.controls[campo];
    return control.invalid && control.touched;
  }
}
