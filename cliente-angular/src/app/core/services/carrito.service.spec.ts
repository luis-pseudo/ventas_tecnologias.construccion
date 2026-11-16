import { TestBed } from '@angular/core/testing';
import { Producto } from '../models/producto.model';
import { CarritoService } from './carrito.service';

const teclado: Producto = { id: 'p1', nombre: 'Teclado', precio: 450 };
const mouse: Producto = { id: 'p2', nombre: 'Mouse', precio: 199.9 };

describe('CarritoService', () => {
  let carrito: CarritoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    carrito = TestBed.inject(CarritoService);
  });

  it('empieza vacío', () => {
    expect(carrito.lineas()).toEqual([]);
    expect(carrito.total()).toBe(0);
    expect(carrito.unidades()).toBe(0);
  });

  it('agrega productos y acumula la cantidad si ya existen', () => {
    carrito.agregar(teclado);
    carrito.agregar(teclado, 2);
    carrito.agregar(mouse);

    expect(carrito.lineas()).toHaveLength(2);
    expect(carrito.lineas()[0].cantidad).toBe(3);
    expect(carrito.unidades()).toBe(4);
  });

  it('calcula el total redondeado a centavos', () => {
    carrito.agregar(mouse, 3); // 599.7
    carrito.agregar(teclado); // 450

    expect(carrito.total()).toBe(1049.7);
  });

  it('cambia la cantidad y la quita si es menor a 1', () => {
    carrito.agregar(teclado);

    carrito.cambiarCantidad('p1', 5);
    expect(carrito.lineas()[0].cantidad).toBe(5);

    carrito.cambiarCantidad('p1', 0);
    expect(carrito.lineas()).toEqual([]);
  });

  it('rechaza cantidades inválidas al agregar (caso negativo)', () => {
    expect(() => carrito.agregar(teclado, 0)).toThrowError(/mayor o igual a 1/);
    expect(() => carrito.agregar(teclado, 1.5)).toThrowError();
    expect(carrito.lineas()).toEqual([]);
  });

  it('genera la solicitud de venta que espera la API', () => {
    carrito.agregar(teclado, 2);
    carrito.agregar(mouse);

    expect(carrito.aSolicitudVenta()).toEqual({
      lineas: [
        { productoId: 'p1', cantidad: 2 },
        { productoId: 'p2', cantidad: 1 },
      ],
    });
  });

  it('quita una línea y vacía el carrito', () => {
    carrito.agregar(teclado);
    carrito.agregar(mouse);

    carrito.quitar('p1');
    expect(carrito.lineas().map((l) => l.producto.id)).toEqual(['p2']);

    carrito.vaciar();
    expect(carrito.lineas()).toEqual([]);
  });
});
