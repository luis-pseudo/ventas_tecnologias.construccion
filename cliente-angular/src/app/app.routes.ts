import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'catalogo' },
  {
    path: 'catalogo',
    title: 'Catálogo · Ventas TCSW',
    loadComponent: () => import('./features/catalogo/catalogo').then((m) => m.Catalogo),
  },
  {
    path: 'carrito',
    title: 'Carrito · Ventas TCSW',
    loadComponent: () => import('./features/carrito/carrito').then((m) => m.Carrito),
  },
  {
    path: 'ventas',
    title: 'Ventas · Ventas TCSW',
    loadComponent: () => import('./features/ventas/ventas').then((m) => m.Ventas),
  },
  { path: '**', redirectTo: 'catalogo' },
];
