import { CurrencyPipe } from '@angular/common';
import { Component, OnInit, inject, signal } from '@angular/core';
import { mensajeDeError } from '../../core/models/api-error.model';
import { Venta } from '../../core/models/venta.model';
import { VentaService } from '../../core/services/venta.service';

type Estado = 'cargando' | 'listo' | 'error';

@Component({
  selector: 'app-ventas',
  imports: [CurrencyPipe],
  templateUrl: './ventas.html',
})
export class Ventas implements OnInit {
  private readonly ventasApi = inject(VentaService);

  protected readonly estado = signal<Estado>('cargando');
  protected readonly ventas = signal<Venta[]>([]);
  protected readonly error = signal('');

  ngOnInit(): void {
    this.cargar();
  }

  protected cargar(): void {
    this.estado.set('cargando');
    this.error.set('');

    this.ventasApi.listar().subscribe({
      next: (ventas) => {
        this.ventas.set(ventas);
        this.estado.set('listo');
      },
      error: (e: unknown) => {
        this.error.set(mensajeDeError(e));
        this.estado.set('error');
      },
    });
  }
}