export interface LineaVentaRequest {
  productoId: string;
  cantidad: number;
}

export interface CrearVentaRequest {
  lineas: LineaVentaRequest[];
}

export interface LineaVenta {
  producto: string;
  cantidad: number;
  precioUnitario: number;
  subtotal: number;
}

export interface Venta {
  id: string;
  lineas: LineaVenta[];
  subtotal: number;
  total: number;
}
