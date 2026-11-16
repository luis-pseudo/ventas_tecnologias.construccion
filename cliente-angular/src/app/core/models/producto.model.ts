export interface Producto {
  id: string;
  nombre: string;
  precio: number;
}

export interface CrearProductoRequest {
  nombre: string;
  precio: number;
}
